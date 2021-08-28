package tj.ilhom.trip.ui.excurse

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tj.ilhom.trip.network.Repository
import javax.inject.Inject

@HiltViewModel
class ExcurseViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
}