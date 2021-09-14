package tj.ilhom.trip.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tj.ilhom.trip.models.PagingResponse
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.review.Review

interface API {
    @GET("/api/cities/")
    suspend fun getCities(
        @Query("page") page: Int,
        @Query("page_size") size: Int = 20
    ): Response<PagingResponse<City>>

    @GET("/api/cities/")
    suspend fun searchCity(
        @Query("name_ru") nameRus: String,
    ): Response<PagingResponse<City>>

    @GET("/api/experiences/{id}/")
    suspend fun getExcursion(
        @Path("id") id: Int,
        @Query("detailed") detailed: Boolean = true,
        @Query("price_format") format: String = "detailed"
    ): Response<Excurse>

    @GET("/api/search/experiences/")
    suspend fun getExperiencesFiltred(
        @Query("page") page: Int,
        @Query("city") cityId: Int,
        @Query("price_format") priceFormat: String = "detailed",
        @Query("detailed") detailed: Boolean = true,
        @Query("start_price") startPrice : Int? = null,
        @Query("end_price") endPrice : Int? = null,
        @Query("start_date") startDate : String? = null,
        @Query("end_date") endDate : String? = null
    ): Response<PagingResponse<Excurse>>

    @GET("/api/experiences/")
    suspend fun getExperiences(
        @Query("page") page: Int,
        @Query("city") cityId: Int,
        @Query("detailed") detailed: Boolean = true,
        @Query("price_format") priceFormat: String = "detailed"
    ): Response<PagingResponse<Excurse>>

    @GET("/api/search/experiences/")
    suspend fun searchByQueryExperiences(
        @Query("page") page: Int,
        @Query("page_size") size: Int = 10,
        @Query("city") city: Int,
        @Query("query") query: String,
        @Query("detailed") detailed: Boolean = true,
        @Query("price_format") priceFormat: String = "detailed"
    ): Response<PagingResponse<Excurse>>

    @GET("/api/experiences/{excurseId}/reviews/")
    suspend fun getExcursionReviews(
        @Path("excurseId") excurseId : Int,
        @Query("page") page :Int = 1
    ):Response<PagingResponse<Review>>
}
