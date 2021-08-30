package tj.ilhom.trip.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.ExcurseResponse

interface API {
    @GET("/api/cities/")
    suspend fun getCities(
        @Query("page") page: Int,
        @Query("page_size") size: Int = 10
    ): Response<CityResponse>

    @GET("/api/cities/")
    suspend fun searchCity(
        @Query("name_ru") nameRus : String,
    ): Response<CityResponse>

    @GET("/api/experiences/")
    suspend fun getExperiences(
        @Query("page") page: Int,
        @Query("city") cityId : Int,
        @Query("detailed") detailed : Boolean = true
    ):Response<ExcurseResponse>

    @GET("/api/search/experiences/")
    suspend fun searchByQueryExperiences(
        @Query("page") page: Int,
        @Query("page_size") size : Int = 10,
        @Query("city") city : Int,
        @Query("query") query : String,
        @Query("detailed") detailed : Boolean = true
    ):Response<ExcurseResponse>
}
