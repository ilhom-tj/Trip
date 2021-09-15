package tj.ilhom.trip.ui.excurseList.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.Utils.getRefreshKey
import tj.ilhom.trip.Utils.toLoadResult
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.network.repo.Repo

class ExcureFilterDataSource(
    private val repo: Repo,
    private val cityId: Int,
    private val filterModel: FilterModel
) : PagingSource<Int, Excurse>() {

    override fun getRefreshKey(state: PagingState<Int, Excurse>) = state.getRefreshKey()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Excurse> {
        return try {
            val nextPageNumber = params.key ?: 1
            repo.filtredExcursion(
                nextPageNumber,
                cityId,
                filterModel = filterModel
            ).toLoadResult()
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
