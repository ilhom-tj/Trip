package tj.ilhom.trip.network.repo

import retrofit2.Response
import tj.ilhom.trip.models.RestV2.CategoryResponse
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.Resource

interface BackendRepo {

    suspend fun getAppCategory(): Resource<CategoryResponse>

    suspend fun getReviews(userId: Int, excursionId: Int): Resource<Review>

    suspend fun postReview(
        userId: Int,
        review: String,
        categoryId: Int,
        excursionId: Int
    ): Resource<Review>

}