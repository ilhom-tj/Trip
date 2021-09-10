package tj.ilhom.trip.ui.cities

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
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tj.ilhom.trip.Utils.debounce
import tj.ilhom.trip.databinding.CitiesFragmentBinding
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.ui.cities.adapter.CityEvents
import tj.ilhom.trip.ui.cities.adapter.CityListAdapter


class CitiesFragment : Fragment(), CityEvents {

    private lateinit var viewModel: CitiesViewModel
    private lateinit var binding: CitiesFragmentBinding
    private lateinit var cityListAdapter: CityListAdapter
    private val searchQuery = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CitiesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CitiesViewModel::class.java)
        cityListAdapter = CityListAdapter(this, this)
        binding.cityList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.cityList.adapter = cityListAdapter


        lifecycleScope.launch {
            viewModel.getCities().collect(cityListAdapter::submitData)
        }

        binding.editSearchCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        searchQuery.debounce(1000).observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.search(it).collect(cityListAdapter::submitData)
            }
        }
    }

    override fun cityOnClick(city: City) {
        val action = CitiesFragmentDirections.actionCitiesFragmentToExcurseFragment2(city)
        findNavController().navigate(action)
    }

}