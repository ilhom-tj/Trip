package tj.ilhom.trip.ui.cities

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.network.Repository
import java.lang.Exception

class CitySearchDataSource(
    private val repo : Repository,
    private val name : String
) : PagingSource<Int, City>() {
    override fun getRefreshKey(state: PagingState<Int, City>): Int {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, City> {
       try {
           val nextPageNumber = params.key ?: 1
           val response = repo.searchCity(name)
           return LoadResult.Page(
               data = response?.body()?.results ?: emptyList(),
               prevKey = null,
               nextKey = null
           )
       }catch (e : Exception){
           return LoadResult.Error(e)
       }
    }

}
