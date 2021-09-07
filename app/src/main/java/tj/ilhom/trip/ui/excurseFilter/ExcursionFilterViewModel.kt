package tj.ilhom.trip.ui.excurseFilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import tj.ilhom.trip.models.excurse.Excurse
import tj.ilhom.trip.network.Repository
import javax.inject.Inject

@HiltViewModel
class ExcursionFilterViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {


}