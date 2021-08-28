package tj.ilhom.trip.network

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import tj.ilhom.trip.models.CityResponse

interface APIHelper {
    suspend fun getCities(page : Int): Response<CityResponse>
    suspend fun searchCity(nameRus : String): Response<CityResponse>
}
