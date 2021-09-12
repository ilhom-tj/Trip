package tj.ilhom.trip.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tj.ilhom.trip.databinding.CitiesFragmentBinding
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.ui.cities.adapter.CityEvents
import tj.ilhom.trip.ui.cities.adapter.CityListAdapter
import tj.ilhom.trip.ui.collectLoadStates
import tj.ilhom.trip.ui.onSearch


@AndroidEntryPoint
class CitiesFragment : Fragment(), CityEvents {

    private val viewModel: CitiesViewModel by activityViewModels()
    private var binding: CitiesFragmentBinding? = null
    private var snackbar: Snackbar? = null
    private val cityListAdapter by lazy { CityListAdapter(this, this) }

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
            viewModel.cities.collect(cityListAdapter::submitData)
        }

        lifecycleScope.launch {
            viewModel.foundCities.collect(cityListAdapter::submitData)
        }

        binding?.editSearchCity?.doOnTextChanged { s, _, _, c ->
            if (c > 1) search(s.toString())
            else lifecycleScope.launch {
                viewModel.getCities()
            }
        }

        binding?.editSearchCity?.onSearch {
            search(binding?.editSearchCity?.text.toString())
            requireContext()
        }

        lifecycleScope.launchWhenCreated {
            cityListAdapter.collectLoadStates(
                binding?.progress,
                binding?.pagingProgressBar
            ) { snackbar = it }
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

    private fun search(q: String) {
        lifecycleScope.launch {
            delay(500)
            viewModel.search(q)
        }
    }

}