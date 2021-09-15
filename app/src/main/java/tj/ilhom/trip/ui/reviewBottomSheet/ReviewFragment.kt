package tj.ilhom.trip.ui.reviewBottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tj.ilhom.trip.R
import tj.ilhom.trip.Utils.CBottomSheetDialogFragment
import tj.ilhom.trip.databinding.ReviewFragmentBinding
import tj.ilhom.trip.ui.collectLoadStates
import tj.ilhom.trip.ui.reviewBottomSheet.adapter.ReviewAdapter

class ReviewFragment : CBottomSheetDialogFragment() {

    private lateinit var binding: ReviewFragmentBinding
    private lateinit var viewModel: ReviewViewModel
    private lateinit var adapter: ReviewAdapter
    private val args: ReviewFragmentArgs by navArgs()
    private var snackbar: Snackbar? = null

    override fun onStart() {
        super.onStart()
        setFullScreen()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ReviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)

        adapter = ReviewAdapter(this)

        binding.reviews.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.send.setOnClickListener {

        }

        binding.close.setOnClickListener {
            this.dismiss()
        }

        binding.reviews.adapter = adapter

        lifecycleScope.launch {
            adapter.collectLoadStates(binding.progress, binding.pagingProgressBar){
                snackbar = it
            }
        }

        lifecycleScope.launch {
            viewModel.getReview(args.excurseId)
                .collect(adapter::submitData)
        }

        ReviewDataSource.count.observe(viewLifecycleOwner) {
            binding.count.text = "$it отзывов"
        }

    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onDestroyView() {
        super.onDestroyView()
        snackbar?.dismiss()
    }

}