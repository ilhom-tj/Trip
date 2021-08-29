package tj.ilhom.trip.ui.excurseFilter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tj.ilhom.trip.R
import tj.ilhom.trip.Utils.BackgroundChanger

class ExcursionFilterFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: ExcursionFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.excursion_filter_fragment, container, false)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        BackgroundChanger.setWindowTransparent(this)
        viewModel = ViewModelProvider(this).get(ExcursionFilterViewModel::class.java)

    }

}