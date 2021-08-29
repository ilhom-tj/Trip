package tj.ilhom.trip.network

import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.ExcurseResponse
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

    override suspend fun getExcursions(page: Int, city: Int): Response<ExcurseResponse> {
        return apiService.getExperiences(
            page = page,
            cityId = city
        )
    }

    override suspend fun searchExcursion(page: Int, city: Int, query: String): Response<ExcurseResponse> {
        return apiService.searchByQueryExperiences(
            page = page,
            city = city,
            query = query
        )
    }


}