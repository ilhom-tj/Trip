package tj.ilhom.trip.network

import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import javax.inject.Inject

class APIHelperImpl @Inject constructor(
    private val apiService: API
) : APIHelper {


    override suspend fun getCities(page: Int): Response<CityResponse> {
        return apiService.getCities(page)
    }

    override suspend fun searchCity(nameRus: String): Response<CityResponse> {
        return apiService.searchCity(nameRus)
    }


}