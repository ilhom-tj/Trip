package tj.ilhom.trip.network.rest

import retrofit2.Response
import retrofit2.http.*
import tj.ilhom.trip.models.RestV2.CategoryResponse
import tj.ilhom.trip.models.review.Review

interface RestServiceV2 {

    @GET("category")
    suspend fun getAppCategory(): Response<CategoryResponse>

    @GET("review")
    suspend fun getReviews(
        @Query("user_id") userId: Int,
        @Query("excursion_id") excursionId: Int
    ): Response<Review>

    @POST("reviews")
    @FormUrlEncoded
    suspend fun postReview(
        @Field("user_id") userId: Int,
        @Field("review") review: String,
        @Field("category_id") categoryId: Int,
        @Field("excursion_id") excursionId: Int
    ): Response<Review>

}