package tj.ilhom.trip.ui.excurseList.dataSources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.network.Repository

class ExcursionDataSource(
    private val repo: Repository,
    private val cityId: Int
) : PagingSource<Int, Excurse>() {

    override fun getRefreshKey(state: PagingState<Int, Excurse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Excurse> {
        return try {
            val nextPageNumber = params.key ?: 1
            Log.e("Next", nextPageNumber.toString())

            val response = repo.getExcursions(nextPageNumber, cityId)?.body()
            LoadResult.Page(
                data = response?.results ?: emptyList(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response!!.results.isEmpty()) null else nextPageNumber + 1,

                )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
