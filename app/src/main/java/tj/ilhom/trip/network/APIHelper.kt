package tj.ilhom.trip.network

import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse

interface APIHelper {
    suspend fun getCities(page : Int): Response<CityResponse>
    suspend fun searchCity(nameRus : String): Response<CityResponse>
}
