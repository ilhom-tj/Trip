package tj.ilhom.trip.ui.excurseList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import tj.ilhom.trip.network.Repository

class ExcursionSearchDataSource(
    private val repo: Repository,
    private val cityId: Int,
    private val query: String
) : PagingSource<Int, Excurse>() {

    override fun getRefreshKey(state: PagingState<Int, Excurse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Excurse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repo.searchExcursionsByQuery(nextPageNumber, cityId, query = query)?.body()
            LoadResult.Page(
                data = response?.results ?: emptyList(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response!!.results.isEmpty()) null else nextPageNumber + 1,
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
