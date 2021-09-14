package tj.ilhom.trip.ui.excursion

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.network.Repo
import tj.ilhom.trip.network.Resource
import javax.inject.Inject

class ExcursionViewModel @AssistedInject constructor(
    @Assisted private val excursionId: Int,
    private val repo: Repo
) : ViewModel() {

    init {
        getExcursion(excursionId)
    }

    companion object {
        fun provideFactory(
            assistedFactory: ExcursionViewModelFactory,
            excursionId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(excursionId) as T
            }
        }
    }

    @AssistedFactory
    interface ExcursionViewModelFactory {
        fun create(excursionId: Int): ExcursionViewModel
    }

    private val _excursion = MutableLiveData<Excurse>()
    val excursion: LiveData<Excurse> = _excursion

    val error = MutableLiveData<String>()

    fun getExcursion(id: Int) {
        viewModelScope.launch {
            when (val response = repo.getExcursion(id)) {
                is Resource.Error -> error.value = response.message
                is Resource.Success -> _excursion.value = response.data
            }
        }
    }

}