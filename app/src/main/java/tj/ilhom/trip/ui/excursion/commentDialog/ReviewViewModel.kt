package tj.ilhom.trip.ui.excursion.commentDialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.Repository
import tj.ilhom.trip.ui.cities.CityDataSource
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    fun getReview(excurseId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewDataSource(
                    repo = repository,
                    id = excurseId
                )
            },
        ).flow
    }
}