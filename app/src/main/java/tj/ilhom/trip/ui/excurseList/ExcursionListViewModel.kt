package tj.ilhom.trip.ui.excurseList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tj.ilhom.trip.models.city.City
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.excurse.Tag
import tj.ilhom.trip.models.filter.FilterModel
import tj.ilhom.trip.network.Repository
import tj.ilhom.trip.ui.excurseList.dataSources.ExcureFilterDataSource
import tj.ilhom.trip.ui.excurseList.dataSources.ExcursionDataSource

class ExcursionListViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val city: City?
) : ViewModel() {

    init {
        viewModelScope.launch {
            city?.let { getExcursion(it) }
        }
    }

    private val _excursions: MutableStateFlow<PagingData<Excurse>> =
        MutableStateFlow(PagingData.empty())
    val excursion: Flow<PagingData<Excurse>> get() = _excursions

    private val filter = MutableLiveData<FilterModel>()

    fun setFilter(filterModel: FilterModel) {
        filter.value = filterModel
    }

    suspend fun filterData(city: Int, filterModel: FilterModel) {
        Pager(
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
                }.filter { item ->
                    filterByMoveType(item.movement_type, filterModel)
                }.filter { item ->
                    filterByTagType(item.tags, filterModel)
                }
            }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .collect(_excursions::emit)
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

    suspend fun getExcursion(city: City) {
        Pager(
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
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .collect(_excursions::emit)
    }

    suspend fun searchExcursion(page: Int, city: City, query: String) {
        Pager(
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
        ).flow
            .flowOn(Dispatchers.IO)
            .cachedIn(CoroutineScope(Dispatchers.IO))
            .collect(_excursions::emit)

    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            city: City?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(city) as T
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted city: City?): ExcursionListViewModel
    }
}