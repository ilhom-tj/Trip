package tj.ilhom.trip.ui.excurseList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
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
import javax.inject.Inject

@AndroidEntryPoint
class ExcursionListFragment : Fragment(), ExcursionEvent {

    @Inject
    lateinit var factory: ExcursionListViewModel.Factory

    private val viewModel: ExcursionListViewModel by activityViewModels {
        ExcursionListViewModel.provideFactory(factory, args.city)
    }

    private lateinit var binding: ExcursionListFragmentBinding
    private val args: ExcursionListFragmentArgs by navArgs()
    private val excurseAdapter: ExcurseAdapter by lazy {
        ExcurseAdapter(this, this)
    }
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

        binding.excurseCity.text = args.city.name_ru

        binding.excursionList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.excursionList.adapter = excurseAdapter

        lifecycleScope.launch {
            viewModel.excursion.collect(excurseAdapter::submitData)
        }

        binding.editSearchExcursion.doOnTextChanged { s, _, _, _ ->
            searchQuery.value = s.toString()
        }

        searchQuery.debounce(1000).observe(viewLifecycleOwner) { query ->
            lifecycleScope.launch {
                viewModel.searchExcursion(1, city = args.city, query)
            }
        }

        binding.filters.setOnClickListener {
            val action =
                ExcursionListFragmentDirections.actionExcurseFragmentToExcursionFilterFragment(args.city.id)
            findNavController().navigate(action)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<FilterModel>("filter")
            ?.observe(viewLifecycleOwner) { filter ->
                lifecycleScope.launch {
                    viewModel.filterData(
                        city = args.city.id,
                        filterModel = filter
                    )
                }
            }
//
//        viewModel.filter.observe(viewLifecycleOwner) {
//            Log.e("vie",it.startPrice.toString())
//                viewModel.filterData(
//                    city = args.city.id,
//                    filterModel = it
//                )
//        }
    }

    override fun excursionClick(excurse: Excurse) {
        val action = ExcursionListFragmentDirections
            .actionExcurseFragmentToExcursionFragment(excurse)
        findNavController().navigate(action)
    }


}