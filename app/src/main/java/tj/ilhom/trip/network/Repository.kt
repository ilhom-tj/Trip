package tj.ilhom.trip.network

import android.util.Log
import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
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

    suspend fun searchCity(name : String) : Response<CityResponse>?{
        return try {
            apiHelper.searchCity(name)
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }
}