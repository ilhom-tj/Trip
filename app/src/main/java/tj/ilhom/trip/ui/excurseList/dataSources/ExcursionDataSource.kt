package tj.ilhom.trip.ui.excurseList.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.Utils.getRefreshKey
import tj.ilhom.trip.Utils.toLoadResult
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.network.Repo

class ExcursionDataSource(
    private val repo: Repo,
    private val cityId: Int
) : PagingSource<Int, Excurse>() {

    override fun getRefreshKey(state: PagingState<Int, Excurse>) = state.getRefreshKey()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Excurse> {
        return try {
            val nextPageNumber = params.key ?: 1
            repo.getExcursions(nextPageNumber, cityId).toLoadResult()
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
