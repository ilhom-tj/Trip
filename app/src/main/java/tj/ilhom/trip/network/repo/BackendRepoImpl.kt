package tj.ilhom.trip.network.repo

import tj.ilhom.trip.network.BaseRepository
import tj.ilhom.trip.network.rest.RestServiceV2
import javax.inject.Inject

class BackendRepoImpl @Inject constructor(
    private val serviceV2: RestServiceV2
) : BaseRepository(), BackendRepo {

    override suspend fun getAppCategory() = safeApiCall { serviceV2.getAppCategory() }

    override suspend fun getReviews(userId: Int, excursionId: Int) = safeApiCall {
        serviceV2.getReviews(userId, excursionId)
    }

    override suspend fun postReview(
        userId: Int,
        review: String,
        categoryId: Int,
        excursionId: Int
    ) = safeApiCall {
        serviceV2.postReview(userId, review, categoryId, excursionId)
    }

}