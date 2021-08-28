package tj.ilhom.trip.ui.cities

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.network.Repository
import java.lang.Exception

class CityDataSource(
    private val repo: Repository
) : PagingSource<Int, City>() {

    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repo.getCitiesTask(nextPageNumber)?.body()
            LoadResult.Page(
                data = response?.results ?: emptyList(),
                prevKey = response?.getPrevPageNumber(),
                nextKey = response?.getNextPageNumber()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun CityResponse.getNextPageNumber() = next
        ?.substringBefore("&page_size=")
        ?.substringAfter("?page=")
        ?.toIntOrNull()

    private fun CityResponse.getPrevPageNumber() = previous
        ?.substringBefore("&page_size=")
        ?.substringAfter("?page=")
        ?.toIntOrNull()
}
