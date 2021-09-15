package tj.ilhom.trip.network.repo

import tj.ilhom.trip.models.PagingResponse
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.models.review.Review
import tj.ilhom.trip.network.Resource


interface Repo {
    suspend fun getCities(page: Int): Resource<PagingResponse<City>>
    suspend fun searchCity(nameRus: String): Resource<PagingResponse<City>>

    suspend fun getExcursions(page: Int, city: Int): Resource<PagingResponse<Excurse>>
    suspend fun searchExcursion(
        page: Int,
        city: Int,
        query: String
    ): Resource<PagingResponse<Excurse>>

    suspend fun getExcursion(id: Int): Resource<Excurse>

    suspend fun getExcursionReviews(excurse: Int, page: Int): Resource<PagingResponse<Review>>

    suspend fun filtredExcursion(
        page: Int,
        city: Int,
        filterModel: FilterModel
    ): Resource<PagingResponse<Excurse>>
}
