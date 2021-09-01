package tj.ilhom.trip.ui.excursion.commentDialog

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.models.review.ReviewResponse
import tj.ilhom.trip.network.Repository
import java.lang.Exception

class ReviewDataSource(
    private val repo: Repository,
    private val id : Int
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repo.getReviews(id,nextPageNumber)?.body()
            Log.e("Response",response?.results?.size.toString())
            LoadResult.Page(
                data = response?.results ?: emptyList(),
                prevKey = response?.getPrevPageNumber(),
                nextKey = response?.getNextPageNumber()
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun ReviewResponse.getNextPageNumber() = next
        .substringBefore("&page_size=")
        .substringAfter("?page=")
        .toIntOrNull()

    private fun ReviewResponse.getPrevPageNumber() = previous
        ?.substringBefore("&page_size=")
        .substringAfter("?page=")
        .toIntOrNull()
}
