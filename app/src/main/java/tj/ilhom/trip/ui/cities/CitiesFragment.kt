package tj.ilhom.trip.ui.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
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

    private val viewModel: CitiesViewModel by activityViewModels()
    private var binding: CitiesFragmentBinding? = null
    private var snackbar: Snackbar? = null
    private val cityListAdapter by lazy { CityListAdapter(this, this) }
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
            viewModel.cities.collect(cityListAdapter::submitData)
        }

        lifecycleScope.launch {
            viewModel.foundCities.collect(cityListAdapter::submitData)
        }

        binding?.editSearchCity?.doOnTextChanged { s, _, _, _ ->
            searchQuery.value = s.toString()
        }

        binding?.editSearchCity?.onSearch {
            searchQuery.value = binding?.editSearchCity?.text.toString()
            requireContext()
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

        searchQuery.debounce(500).observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.search(it)
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
    return view?.let { v ->
        Snackbar.make(v, "Нет подключения к интернету", Snackbar.LENGTH_INDEFINITE)
            .setAction("Повторить", action)
            .also { it.show() }
    }
}

fun EditText.onSearch(callback: () -> Context) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val context = callback.invoke()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(this.windowToken, 0)
            return@setOnEditorActionListener true
        }
        false
    }
}