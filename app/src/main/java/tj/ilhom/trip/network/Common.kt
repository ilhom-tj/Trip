package tj.ilhom.trip.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T>  = withContext(Dispatchers.IO){
        return@withContext try {
            val response = call()
            if (response.isSuccessful) Resource.Success(response.body()!!)
            else Resource.Error(response.message())
        } catch (e: Exception) {
            println(e.message)
            Resource.Error(e.message.toString())
        }
    }
}