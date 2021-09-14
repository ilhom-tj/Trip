package tj.ilhom.trip.ui.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.network.Repo
import tj.ilhom.trip.network.Resource
import tj.ilhom.trip.ui.toPagingData
import javax.inject.Inject

typealias MViewState<K> = MutableLiveData<Resource<PagingData<K>>>
typealias ViewState<K> = LiveData<Resource<PagingData<K>>>

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {

    private val _cities = MViewState<City>()
    val cities: ViewState<City> = _cities

    private val _foundCities = MViewState<City>()
    val foundCities: ViewState<City> = _foundCities

    init {
        viewModelScope.launch {
            getCities()
        }
    }

    suspend fun getCities() {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CityDataSource(
                    repo = repo
                )
            },
        ).flow
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .collect {
                _cities.value = Resource.Success(it)
            }
    }

    fun search(name: String) {
        viewModelScope.launch {
            when (val response = repo.searchCity(name)) {
                is Resource.Error -> _foundCities.value = Resource.Error(response.message)
                is Resource.Success -> _foundCities.value = Resource.Success(
                    response.data.results.toPagingData()
                )
            }
        }
    }
}