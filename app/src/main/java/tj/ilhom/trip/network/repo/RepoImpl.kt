package tj.ilhom.trip.network.repo

import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.network.BaseRepository
import tj.ilhom.trip.network.rest.RestService
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val service: RestService
) : BaseRepository(), Repo {

    override suspend fun getCities(page: Int) = safeApiCall { service.getCities(page) }

    suspend fun getCitiesTask(page: Int) =
        safeApiCall { service.getCities(page) }

    override suspend fun searchCity(nameRus: String) = safeApiCall { service.searchCity(nameRus) }


    override suspend fun getExcursions(page: Int, city: Int) = safeApiCall {
        service.getExperiences(
            page = page,
            cityId = city
        )
    }

    override suspend fun searchExcursion(
        page: Int,
        city: Int,
        query: String
    ) = safeApiCall {
        service.searchByQueryExperiences(
            page = page,
            city = city,
            query = query
        )
    }

    override suspend fun getExcursion(id: Int) = safeApiCall {
        service.getExcursion(id)
    }

    override suspend fun getExcursionReviews(excurse: Int, page: Int) = safeApiCall {
        service.getExcursionReviews(excurse, page)
    }

    override suspend fun filtredExcursion(
        page: Int,
        city: Int,
        filterModel: FilterModel
    ) = safeApiCall {
        service.getExperiencesFiltred(
            page = page,
            cityId = city,
            startPrice = filterModel.startPrice,
            endPrice = filterModel.endPrice,
            startDate = filterModel.startDate,
            endDate = filterModel.endDate
        )
    }

}