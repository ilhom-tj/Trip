package tj.ilhom.trip.ui.excurseFilter

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.innovattic.rangeseekbar.RangeSeekBar
import dagger.hilt.android.AndroidEntryPoint
import tj.ilhom.trip.R
import tj.ilhom.trip.databinding.ExcursionFilterFragmentBinding
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.ui.excurseFilter.adapter.FilterAdapter
import tj.ilhom.trip.ui.excurseFilter.adapter.SwitcherOnclick
import tj.ilhom.trip.ui.excurseList.ExcursionListViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ExcursionFilterFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var factory: ExcursionListViewModel.Factory

    private val viewModel: ExcursionListViewModel by activityViewModels {
        ExcursionListViewModel.provideFactory(factory, null)
    }

    private lateinit var binding: ExcursionFilterFragmentBinding

    private val args: ExcursionFilterFragmentArgs by navArgs()

    private var priceFrom = MutableLiveData<Int>()
    private val priceTo = MutableLiveData<Int>()

    private lateinit var excursionTypeAdapter: FilterAdapter
    private val excurseType = arrayListOf(
        "Все",
        "Индивидуальные",
        "Групповые"
    )
    private lateinit var excursionMoveAdapter: FilterAdapter
    private val excurseMoveType = arrayListOf(
        "Любой",
        "На машине",
        "Пешком",
        "На автобусе",
        "На кораблике",
        "На велосипеде",
        "В помещении"
    )
    private lateinit var excursionTagTypeAdapter: FilterAdapter
    private val excurseTagType = arrayListOf(
        "Обзорные",
        "Со скидкой",
        "За городом",
        "История и культура",
        "Необычные маршруты",
        "Речные прогулки",
        "Форты",
        "Фотоссесии",
        "Гастрономические",
        "Для детей",
        "Музеи и искуство",
        "Активный отдых"
    )


    val filter = FilterModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExcursionFilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        excursionTypeAdapter = FilterAdapter(this, object : SwitcherOnclick {
            override fun toggle(filter: String, isChecked: Boolean) {
                when (isChecked) {
                    true -> this@ExcursionFilterFragment.filter.tripType.add(filter)
                    false -> this@ExcursionFilterFragment.filter.tripType.remove(filter)
                }
            }
        })
        excursionTypeAdapter.setData(excurseType)


        excursionMoveAdapter = FilterAdapter(this, object : SwitcherOnclick {
            override fun toggle(filter: String, isChecked: Boolean) {
                when (isChecked) {
                    true -> {
                        this@ExcursionFilterFragment.filter.tripMoveType.add(filter)
                    }
                    false -> {
                        this@ExcursionFilterFragment.filter.tripMoveType.remove(filter)
                    }
                }
            }
        })
        excursionMoveAdapter.setData(excurseMoveType)

        excursionTagTypeAdapter = FilterAdapter(this, object : SwitcherOnclick {
            override fun toggle(filter: String, isChecked: Boolean) {
                when (isChecked) {
                    true -> {
                        this@ExcursionFilterFragment.filter.tripTagType.add(filter)
                    }
                    false -> {
                        this@ExcursionFilterFragment.filter.tripTagType.remove(filter)
                    }
                }
            }
        })
        excursionTagTypeAdapter.setData(excurseTagType)



        binding.excursionTypeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.excursionTypeRecycler.adapter = excursionTypeAdapter

        binding.excursionMovementRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.excursionMovementRecycler.adapter = excursionMoveAdapter

        binding.excursionTagTypeRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.excursionTagTypeRecycler.adapter = excursionTagTypeAdapter


        binding.rangeSeekBar.max = 15000
        binding.rangeSeekBar.minRange = 0

        binding.cardView.setOnClickListener {
            filter.startDate = binding.dateFromEdt.text.toString()
            filter.endDate = binding.dateToEdt.text.toString()
            val mutableFilter = MutableLiveData<FilterModel>()
            mutableFilter.value = filter

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "filter",
                mutableFilter.value
            )
            findNavController().navigateUp()
        }
        binding.rangeSeekBar.seekBarChangeListener =
            object : RangeSeekBar.SeekBarChangeListener {
                override fun onStartedSeeking() {

                }

                override fun onStoppedSeeking() {

                }

                override fun onValueChanged(minThumbValue: Int, maxThumbValue: Int) {
                    priceFrom.value = minThumbValue
                    priceTo.value = maxThumbValue
                }

            }

        priceFrom.observe(viewLifecycleOwner) {
            it.let { price ->
                filter.startPrice = price.toDouble()
                binding.priceFromEdt.setText("$price ₽")
            }
        }

        priceTo.observe(viewLifecycleOwner) {
            it.let { price ->
                filter.endPrice = price.toDouble()
                binding.priceToEdit.setText("$price ₽")
            }
        }



        binding.dateFromEdt.transformIntoDatePicker(requireContext(), "yyyy-MM-dd", Date())

        binding.dateToEdt.transformIntoDatePicker(requireContext(), "yyyy-MM-dd", Date())

    }

    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
