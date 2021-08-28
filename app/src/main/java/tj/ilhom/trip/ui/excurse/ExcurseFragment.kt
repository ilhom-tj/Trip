package tj.ilhom.trip.ui.excurse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import tj.ilhom.trip.databinding.ExcurseFragmentBinding

class ExcurseFragment : Fragment() {


    private lateinit var viewModel: ExcurseViewModel
    private lateinit var binding: ExcurseFragmentBinding
    private val args: ExcurseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExcurseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExcurseViewModel::class.java)
        binding.excurseCity.text = args.city.name_ru
    }

}