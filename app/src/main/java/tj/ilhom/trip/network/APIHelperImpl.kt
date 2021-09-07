package tj.ilhom.trip.network

import retrofit2.Call
import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import tj.ilhom.trip.models.review.ReviewResponse
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

    override fun getExcursion(id: Int): Call<Excurse> {
        return apiService.getExcursion(id)
    }

    override suspend fun getExcursionReviews(excurse: Int,page:Int): Response<ReviewResponse> {
        return apiService.getExcursionReviews(excurse,page)
    }

    override suspend fun filtredExcursion(
        page: Int,
        city: Int,
        startPrice: Double?,
        endPrice: Double?,
        startDate: String?,
        endDate: String?
    ): Response<ExcurseResponse> {
        return apiService.getExperiencesFiltred(
            page = page,
            cityId = city,
            startPrice = startPrice.toString(),
            endPrice =  endPrice.toString(),
            startDate = startDate,
            endDate = endDate
        )
    }


}