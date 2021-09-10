package tj.ilhom.trip.ui.excurseList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tj.ilhom.trip.Utils.debounce
import tj.ilhom.trip.databinding.ExcursionListFragmentBinding
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.ui.excurseList.adapter.ExcurseAdapter
import tj.ilhom.trip.ui.excurseList.adapter.ExcursionEvent

@AndroidEntryPoint
class ExcursionListFragment : Fragment(), ExcursionEvent {

    private lateinit var _view: View
    private lateinit var viewModel: ExcursionListViewModel
    private lateinit var binding: ExcursionListFragmentBinding
    private val args: ExcursionListFragmentArgs by navArgs()
    private lateinit var excurseAdapter: ExcurseAdapter
    private val searchQuery = MutableLiveData<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ExcursionListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExcursionListViewModel::class.java)

        binding.excurseCity.text = args.city.name_ru

        excurseAdapter = ExcurseAdapter(this, this)
        binding.excursionList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.excursionList.adapter = excurseAdapter


        lifecycleScope.launch {
            viewModel.getExcursion(args.city).collect(excurseAdapter::submitData)
        }

        binding.editSearchExcursion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        searchQuery.debounce(1000).observe(viewLifecycleOwner) { query ->
            lifecycleScope.launch {
                viewModel.searchExcursion(1, city = args.city, query)
                    .collect(excurseAdapter::submitData)
            }
        }

        binding.filters.setOnClickListener {
            val action = ExcursionListFragmentDirections.actionExcurseFragmentToExcursionFilterFragment(args.city.id)
            findNavController().navigate(action)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<FilterModel>("filter")
            ?.observe(viewLifecycleOwner) { filter ->
                lifecycleScope.launch {
                    viewModel.filterData(
                        city = args.city.id,
                        filterModel = filter
                    ).collect(excurseAdapter::submitData)
                }
            }
    }
    override fun excursionClick(excurse: Excurse) {
        val action = ExcursionListFragmentDirections
            .actionExcurseFragmentToExcursionFragment(excurse)
        findNavController().navigate(action)
    }
}




