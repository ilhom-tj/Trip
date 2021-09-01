package tj.ilhom.trip.ui.excurseList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.network.Repository
import javax.inject.Inject

@HiltViewModel
class ExcursionListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getExcursion( city: City): Flow<PagingData<Excurse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ExcursionDataSource(
                    repo = repository,
                    cityId = city.id
                )
            },
        ).flow
    }
    fun searchExcursion(page: Int, city: City,query: String): Flow<PagingData<Excurse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ExcursionSearchDataSource(
                    repo = repository,
                    cityId = city.id,
                    query = query
                )
            },
        ).flow.cachedIn(CoroutineScope(Dispatchers.IO))
    }
}