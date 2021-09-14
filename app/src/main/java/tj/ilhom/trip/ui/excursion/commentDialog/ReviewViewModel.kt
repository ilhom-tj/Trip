package tj.ilhom.trip.ui.excursion.commentDialog

import androidx.lifecycle.ViewModel
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.RepoImpl
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    val repoImpl: RepoImpl
) : ViewModel() {
    fun getReview(excurseId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewDataSource(
                    repo = repoImpl,
                    id = excurseId
                )
            },
        ).flow
    }
}