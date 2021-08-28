package tj.ilhom.trip.ui.excurse

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tj.ilhom.trip.R

class ExcurseFragment : Fragment() {

    companion object {
        fun newInstance() = ExcurseFragment()
    }

    private lateinit var viewModel: ExcurseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.excurse_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExcurseViewModel::class.java)
        // TODO: Use the ViewModel
    }

}