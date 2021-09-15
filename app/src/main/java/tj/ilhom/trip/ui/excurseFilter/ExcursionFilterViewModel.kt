package tj.ilhom.trip.ui.excurseFilter

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tj.ilhom.trip.network.repo.RepoImpl
import javax.inject.Inject

@HiltViewModel
class ExcursionFilterViewModel @Inject constructor(
    val repoImpl: RepoImpl
) : ViewModel() {


}