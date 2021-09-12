package tj.ilhom.trip.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.network.Repository
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _cities = MutableStateFlow<PagingData<City>>(PagingData.empty())
    val cities: Flow<PagingData<City>> = _cities

    private val _foundCities = MutableStateFlow<PagingData<City>>(PagingData.empty())
    val foundCities: Flow<PagingData<City>> = _foundCities

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
                    repo = repository
                )
            },
        ).flow
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .collect(_cities::emit)
    }

    suspend fun search(name: String) {
        val response = repository.searchCity(name)
        if (response?.isSuccessful == true) {
            val data = response.body()!!.results
            _foundCities.emit(PagingData.from(data))
        }
    }
}