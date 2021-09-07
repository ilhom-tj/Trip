package tj.ilhom.trip.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tj.ilhom.trip.models.city.CityResponse
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.ExcurseResponse
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.models.review.ReviewResponse
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

    suspend fun filterExcursions(
        page: Int,
        city: Int,
        filterModel: FilterModel
    ): Response<ExcurseResponse>? {
        Log.e("Network","working")
        return try {
            apiHelper.filtredExcursion(
                page = page,
                city = city,
                startPrice = filterModel.startPrice,
                endPrice = filterModel.endPrice,
                startDate = filterModel.startDate,
                endDate = filterModel.endDate
            )
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }

    fun getExcursion(id: Int): LiveData<Excurse> {

        val data = MutableLiveData<Excurse>()
        apiHelper.getExcursion(id).enqueue(object : Callback<Excurse> {
            override fun onResponse(call: Call<Excurse>, response: Response<Excurse>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<Excurse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
        return data
    }

    suspend fun getReviews(id: Int, page: Int): Response<ReviewResponse>? {
        return try {
            apiHelper.getExcursionReviews(id, page)
        } catch (e: Exception) {
            Log.e("Exeption", e.message.toString())
            return null
        }
    }
}