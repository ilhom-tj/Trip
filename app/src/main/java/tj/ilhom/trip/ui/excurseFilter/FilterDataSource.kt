package tj.ilhom.trip.ui.excurseFilter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.network.Repository

class FilterDataSource(
    private val repo: Repository,
    private val city: Int,
    private val filterModel: FilterModel
) : PagingSource<Int, Excurse>() {

    override fun getRefreshKey(state: PagingState<Int, Excurse>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Excurse> {
        return try {
            Log.e("dsa","dsadsa")
            val nextPageNumber = params.key ?: 1
            val response = repo.filterExcursions(
                nextPageNumber,
                city = city,
                filterModel = filterModel
            )
            Log.e("Response", response?.body().toString())
            LoadResult.Page(
                data = response?.body()?.results ?: emptyList(),
                prevKey = response?.body()?.getPrevPageNumber(),
                nextKey = response?.body()?.getNextPageNumber()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun ExcurseResponse.getNextPageNumber() = next
        ?.substringBefore("&page_size=")
        ?.substringAfter("?page=")
        ?.toIntOrNull()

    private fun ExcurseResponse.getPrevPageNumber() = previous
        ?.substringBefore("&page_size=")
        ?.substringAfter("?page=")
        ?.toIntOrNull()
}
