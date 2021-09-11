package tj.ilhom.trip.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.ilhom.trip.Utils.debounce
import tj.ilhom.trip.databinding.CitiesFragmentBinding
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.ui.cities.adapter.CityEvents
import tj.ilhom.trip.ui.cities.adapter.CityListAdapter

@AndroidEntryPoint
class CitiesFragment : Fragment(), CityEvents {

    private val viewModel: CitiesViewModel by viewModels()
    private var binding: CitiesFragmentBinding? = null
    private var snackbar: Snackbar? = null
    private val cityListAdapter by lazy { CityListAdapter(this) }
    private val searchQuery = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CitiesFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.cityList?.layoutManager = GridLayoutManager(requireContext(), 1)
        binding?.cityList?.adapter = cityListAdapter

        lifecycleScope.launch {
            viewModel.getCities().collect(cityListAdapter::submitData)
        }

        binding?.editSearchCity?.doOnTextChanged { s, _, _, _ ->
            searchQuery.value = s.toString()
        }

        lifecycleScope.launchWhenCreated {
            cityListAdapter.loadStateFlow.collectLatest { states ->
                binding?.pagingProgressBar?.isVisible =
                    states.append is LoadState.Loading && binding?.progress?.isVisible == false
                if (!cityListAdapter.snapshot().isNullOrEmpty()) {
                    binding?.progress?.isVisible = false
                }
                when {
                    states.append is LoadState.Error || states.refresh is LoadState.Error ->
                        snackbar = noInternetSnackBar(binding?.cityList) { cityListAdapter.retry() }
                }
            }
        }

        searchQuery.debounce(1000).observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.search(it).collect(cityListAdapter::submitData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        snackbar?.dismiss()
    }

    override fun cityOnClick(city: City) {
        val action = CitiesFragmentDirections.actionCitiesFragmentToExcurseFragment2(city)
        findNavController().navigate(action)
    }

}

fun noInternetSnackBar(view: View?, action: (View) -> Unit): Snackbar? {
    return view?.let {
        Snackbar.make(it, "Нет подключения к интернету", Snackbar.LENGTH_INDEFINITE)
            .setAction("Повторить", action)
            .also { it.show() }
    }
}