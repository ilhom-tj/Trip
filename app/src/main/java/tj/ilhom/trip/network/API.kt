package tj.ilhom.trip.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import tj.ilhom.trip.models.review.ReviewResponse

interface API {
    @GET("/api/cities/")
    suspend fun getCities(
        @Query("page") page: Int,
        @Query("page_size") size: Int = 20
    ): Response<CityResponse>

    @GET("/api/cities/")
    suspend fun searchCity(
        @Query("name_ru") nameRus: String,
    ): Response<CityResponse>

    @GET("/api/experiences/{id}/")
    fun getExcursion(
        @Path("id") id: Int,
        @Query("detailed") detailed: Boolean = true,
        @Query("price_format") format: String = "detailed"
    ): Call<Excurse>

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
    ): Response<ExcurseResponse>

    @GET("/api/experiences/")
    suspend fun getExperiences(
        @Query("page") page: Int,
        @Query("city") cityId: Int,
        @Query("detailed") detailed: Boolean = true,
        @Query("price_format") priceFormat: String = "detailed"
    ): Response<ExcurseResponse>

    @GET("/api/search/experiences/")
    suspend fun searchByQueryExperiences(
        @Query("page") page: Int,
        @Query("page_size") size: Int = 10,
        @Query("city") city: Int,
        @Query("query") query: String,
        @Query("detailed") detailed: Boolean = true,
        @Query("price_format") priceFormat: String = "detailed"
    ): Response<ExcurseResponse>

    @GET("/api/experiences/{excurseId}/reviews/")
    suspend fun getExcursionReviews(
        @Path("excurseId") excurseId : Int,
        @Query("page") page :Int = 1
    ):Response<ReviewResponse>
}
