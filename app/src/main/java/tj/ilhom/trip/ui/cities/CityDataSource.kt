package tj.ilhom.trip.ui.cities

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.Utils.getRefreshKey
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.network.Repo
import tj.ilhom.trip.Utils.toLoadResult

class CityDataSource(
    private val repo: Repo
) : PagingSource<Int, City>() {

    override fun getRefreshKey(state: PagingState<Int, City>) = state.getRefreshKey()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return try {
            val nextPageNumber = params.key ?: 1
            repo.getCities(nextPageNumber).toLoadResult()
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}