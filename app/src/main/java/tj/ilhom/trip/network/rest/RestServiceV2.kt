package tj.ilhom.trip.network.rest

import retrofit2.Response
import retrofit2.http.*
import tj.ilhom.trip.models.RestV2.CategoryV2
import tj.ilhom.trip.models.RestV2.ReviewV2
import tj.ilhom.trip.models.RestV2.V2Response

interface RestServiceV2 {

    @GET("category")
    suspend fun getAppCategory(): Response<V2Response<CategoryV2>>

    @GET("review")
    suspend fun getReviews(
        @Query("excursion_id") excursionId: Int
    ): Response<V2Response<ReviewV2>>

    @POST("reviews")
    @FormUrlEncoded
    suspend fun postReview(
        @Field("user_id") userId: Int,
        @Field("review") review: String,
        @Field("category_id") categoryId: Int = 2,
        @Field("excursion_id") excursionId: Int
    ): Response<V2Response<ReviewV2>>

}