package tj.ilhom.trip.ui.excurseList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.Tag
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.network.Repository
import tj.ilhom.trip.ui.excurseList.dataSources.ExcureFilterDataSource
import tj.ilhom.trip.ui.excurseList.dataSources.ExcursionDataSource
import javax.inject.Inject

@HiltViewModel
class ExcursionListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val filter = MutableLiveData<FilterModel>()


    fun setFilter(filterModel: FilterModel) {
        filter.value = filterModel
    }


    fun filterData(
        city: Int,
        filterModel: FilterModel
    ): Flow<PagingData<Excurse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ExcureFilterDataSource(
                    repo = repository,
                    cityId = city,
                    filterModel = filterModel
                )
            },
        ).flow
            .map {
                it.filter { item ->
                    filterByType(item.type, filterModel)
                }
            }.map {
                it.filter { item ->
                    filterByMoveType(item.movement_type, filterModel)
                }
            }.map {
                it.filter { item ->
                    filterByTagType(item.tags, filterModel)
                }
            }


    }


    private fun filterByTagType(tags: List<Tag>, filterModel: FilterModel): Boolean {
        tags.forEach {
            if (checkTag(it.name, filterModel)) {
                return true
            }
        }
        return false
    }

    fun checkTag(tag: String, filterModel: FilterModel): Boolean {
        filterModel.tripTagType.forEach {
            if (tag == it) {
                return true
            }
        }
        return false
    }

    fun filterByType(type: String, filterModel: FilterModel): Boolean {
        val typeStr = when (type) {
            "private" -> "Индивидуальные"
            "group" -> "Групповые"
            else -> "Все"
        }
        filterModel.tripType.forEach {
            if (it == "Все") {
                return true
            }
            return typeStr == it
        }
        return true
    }

    fun filterByMoveType(type: String, filterModel: FilterModel): Boolean {

        var moveType = when (type) {
            "foot" -> {
                "Пешком"
            }
            "car" -> {
                "На машине"
            }
            "bicycle" -> {
                "На велосипеде"
            }
            "bus" -> {
                "На автобусе"
            }
            "motorcycle" -> {
                "На мотоцикле"
            }
            "watership" -> {
                "На кораблике"
            }
            else -> "Другое"
        }

        filterModel.tripMoveType.forEach {
            if (it == "Любой") {
                return true
            }
            return moveType == it
        }
        return true
    }

    fun getExcursion(city: City): Flow<PagingData<Excurse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ExcursionDataSource(
                    repo = repository,
                    cityId = city.id
                )
            },
        ).flow
    }

    fun searchExcursion(page: Int, city: City, query: String): Flow<PagingData<Excurse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ExcursionSearchDataSource(
                    repo = repository,
                    cityId = city.id,
                    query = query
                )
            },
        ).flow.cachedIn(CoroutineScope(Dispatchers.IO))
    }


}