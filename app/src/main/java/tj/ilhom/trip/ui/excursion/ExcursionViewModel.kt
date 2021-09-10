package tj.ilhom.trip.ui.excursion

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.models.review.ReviewResponse
import tj.ilhom.trip.network.Repository
import javax.inject.Inject

@HiltViewModel
class ExcursionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun getExcursion(id: Int): LiveData<Excurse> {
        return repository.getExcursion(id)
    }

}