package tj.ilhom.trip.network

import android.util.Log
import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiHelper: APIHelper
) {
    suspend fun getCities(page: Int) = apiHelper.getCities(page)


    suspend fun getCitiesTask(page: Int): Response<CityResponse>? {
        return try {
            apiHelper.getCities(page)
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }

    suspend fun searchCity(name: String): Response<CityResponse>? {
        return try {
            apiHelper.searchCity(name)
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }

    suspend fun getExcursions(page: Int, city: Int): Response<ExcurseResponse>? {
        return try {
            apiHelper.getExcursions(
                page = page,
                city = city
            )
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }

    suspend fun searchExcursionsByQuery(page: Int, city: Int, query: String): Response<ExcurseResponse>? {
        return try {
            apiHelper.searchExcursion(
                page = page,
                city = city,
                query = query
            )
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }
}