package tj.ilhom.trip.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tj.ilhom.trip.models.CityResponse

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
}
