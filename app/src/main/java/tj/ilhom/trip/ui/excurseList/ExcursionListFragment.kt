package tj.ilhom.trip.ui.excurseList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tj.ilhom.trip.R
import tj.ilhom.trip.databinding.ExcursionListFragmentBinding
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.ui.collectLoadStates
import tj.ilhom.trip.ui.excurseList.adapter.ExcurseAdapter
import tj.ilhom.trip.ui.excurseList.adapter.ExcursionEvent
import tj.ilhom.trip.ui.onSearch

@AndroidEntryPoint
class ExcursionListFragment : Fragment(), ExcursionEvent {

    private var snackbar: Snackbar? = null
    private val viewModel: ExcursionListViewModel by navGraphViewModels(R.id.excurseFragment) {
        defaultViewModelProviderFactory
    }
    private lateinit var binding: ExcursionListFragmentBinding
    private val args: ExcursionListFragmentArgs by navArgs()
    private lateinit var excurseAdapter: ExcurseAdapter

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

        excurseAdapter = ExcurseAdapter(this, this)
        binding.excursionList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.excursionList.adapter = excurseAdapter


        lifecycleScope.launch {
            viewModel.getExcursion(args.city).collect(excurseAdapter::submitData)
        }

        binding.editSearchExcursion.onSearch {
            search(binding.editSearchExcursion.text.toString())
            requireContext()
        }

        lifecycleScope.launchWhenCreated {
            excurseAdapter.collectLoadStates(binding.progress, binding.pagingProgressBar) {
                snackbar = it
            }
        }

        binding.editSearchExcursion.doOnTextChanged { text, start, before, count ->
            if (count > 1) search(text.toString())
            else lifecycleScope.launch {
                viewModel.getExcursion(args.city).collect(excurseAdapter::submitData)
            }
        }

        binding.filters.setOnClickListener {
            val action =
                ExcursionListFragmentDirections.actionExcurseFragmentToExcursionFilterFragment(args.city.country)
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

    private fun search(query: String) {
        lifecycleScope.launch {
            viewModel.searchExcursion(city = args.city, query)
                .collect(excurseAdapter::submitData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackbar?.dismiss()

    }
}




