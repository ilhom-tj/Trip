package tj.ilhom.trip.ui.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.network.Repository
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun getCities(page: Int): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CityDataSource(
                    repo = repository
                )
            },
        ).flow.cachedIn(viewModelScope)
    }
    fun search(name: String): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CitySearchDataSource(
                    repo = repository,
                    name = name
                )
            },
        ).flow.cachedIn(viewModelScope)
    }
}