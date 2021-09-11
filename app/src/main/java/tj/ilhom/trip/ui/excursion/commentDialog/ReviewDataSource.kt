package tj.ilhom.trip.ui.excursion.commentDialog

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.models.review.ReviewResponse
import tj.ilhom.trip.network.Repository

class ReviewDataSource(
    private val repo: Repository,
    private val id: Int
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
            val response = repo.getReviews(id, nextPageNumber)?.body()
            Log.e("Response", response?.results?.size.toString())
            count.postValue(response?.count ?: 0)
            LoadResult.Page(
                data = response?.results ?: emptyList(),
                prevKey = response?.getPrevPageNumber(),
                nextKey = response?.getNextPageNumber()
            )
        } catch (e: Exception) {
            Log.e("Exception", e.message.toString())
            LoadResult.Error(e)
        } catch (http: HttpException) {
            Log.e("Exception", http.message.toString())
            LoadResult.Error(http)
        }
    }

    private fun ReviewResponse.getNextPageNumber() = next
        ?.substringBefore("&page_size=")
        ?.substringAfter("?page=")
        ?.toIntOrNull()

    private fun ReviewResponse.getPrevPageNumber() = previous
        ?.substringBefore("&page_size=")
        ?.substringAfter("?page=")
        ?.toIntOrNull()

    companion object{
        var count = MutableLiveData(0)
    }
}
