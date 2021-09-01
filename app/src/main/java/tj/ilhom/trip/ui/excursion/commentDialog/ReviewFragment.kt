package tj.ilhom.trip.ui.excursion.commentDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tj.ilhom.trip.databinding.ReviewFragmentBinding
import tj.ilhom.trip.ui.excursion.commentDialog.adapter.ReviewAdapter

class ReviewFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ReviewFragmentBinding
    private lateinit var viewModel: ReviewViewModel
    private lateinit var adapter: ReviewAdapter
    private val args: ReviewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ReviewAdapter(this)
        binding.reviews.layoutManager = GridLayoutManager(requireContext(),1)
        binding.reviews.adapter = adapter
        binding.reviews.setHasFixedSize(true)
        binding.reviews.isNestedScrollingEnabled = true
        viewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        lifecycleScope.launch {
            viewModel.getReview(args.excurseID).collect(adapter::submitData)
        }

    }

}