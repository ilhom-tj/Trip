package tj.ilhom.trip.network

import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.ExcurseResponse

interface APIHelper {
    suspend fun getCities(page: Int): Response<CityResponse>
    suspend fun searchCity(nameRus: String): Response<CityResponse>

    suspend fun getExcursions(page: Int, city: Int): Response<ExcurseResponse>
    suspend fun searchExcursion(page: Int, city: Int, query: String): Response<ExcurseResponse>


}
