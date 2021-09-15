package tj.ilhom.trip.network.repo

import tj.ilhom.trip.models.RestV2.CategoryV2
import tj.ilhom.trip.models.RestV2.ReviewV2
import tj.ilhom.trip.models.RestV2.V2Response
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.Resource

interface BackendRepo {

    suspend fun getAppCategory(): Resource<V2Response<CategoryV2>>

    suspend fun getReviews(excursionId: Int): Resource<V2Response<ReviewV2>>

    suspend fun postReview(
        userId: Int,
        review: String,
        categoryId: Int,
        excursionId: Int
    ): Resource<V2Response<ReviewV2>>
}