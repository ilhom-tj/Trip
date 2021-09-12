package tj.ilhom.trip.network

import retrofit2.Call
import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import tj.ilhom.trip.models.review.ReviewResponse

interface APIHelper {
    suspend fun getCities(page: Int): Response<CityResponse>
    suspend fun searchCity(nameRus: String): Response<CityResponse>

    suspend fun getExcursions(page: Int, city: Int): Response<ExcurseResponse>
    suspend fun searchExcursion(page: Int, city: Int, query: String): Response<ExcurseResponse>

    fun getExcursion(id: Int): Call<Excurse>

    suspend fun getExcursionReviews(excurse: Int, page: Int): Response<ReviewResponse>

    suspend fun filtredExcursion(
        page: Int,
        city : Int,
        startPrice: Int? = null,
        endPrice: Int? = null,
        startDate: String? = null,
        endDate: String? = null
    ): Response<ExcurseResponse>
}
