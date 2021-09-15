package tj.ilhom.trip.ui.reviewBottomSheet

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.repo.BackendRepo
import tj.ilhom.trip.network.repo.RepoImpl
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repoImpl: RepoImpl,
    private val backendRepo: BackendRepo
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
                    backendRepo = backendRepo,
                    id = excurseId
                )
            },
        ).flow
    }
}