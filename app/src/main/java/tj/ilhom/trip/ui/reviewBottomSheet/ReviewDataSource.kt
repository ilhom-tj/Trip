package tj.ilhom.trip.ui.reviewBottomSheet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.Utils.getRefreshKey
import tj.ilhom.trip.Utils.toLoadResult
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.repo.Repo
import tj.ilhom.trip.network.Resource
import tj.ilhom.trip.network.repo.BackendRepo

class ReviewDataSource(
    private val repo: Repo,
    private val backendRepo: BackendRepo,
    private val id: Int
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>) = state.getRefreshKey()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repo.getExcursionReviews(id, nextPageNumber)
            if (response is Resource.Success)
                count.postValue(response.data.count)
            response.toLoadResult()
        } catch (e: Exception) {
            Log.e("Exception", e.message.toString())
            LoadResult.Error(e)
        }
    }

    companion object{
        var count = MutableLiveData(0)
    }
}