package tj.ilhom.trip.ui.excursion.descriptionModal

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tj.ilhom.trip.R
import tj.ilhom.trip.databinding.DescriptionFragmentBinding

class DescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: DescriptionViewModel
    private lateinit var binding: DescriptionFragmentBinding
    private val args: DescriptionFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DescriptionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DescriptionViewModel::class.java)
        binding.apply {
            Log.e("Desc", args.excursion.description.toString())
            if (args.excursion.description != null) {
                description.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(args.excursion.description.toString(), Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(args.excursion.description.toString())
                }
            }

            Glide.with(requireContext())
                .load(args.excursion.guide.avatar.medium)
                .into(binding.guideAvatar)
            guideName.text = args.excursion.guide.first_name
            if (!args.excursion.guide.description.isNullOrEmpty()) {
                guideDescription.text = args.excursion.guide.description.toString()
            } else {
                guideDescription.isVisible = false
            }
            if (args.excursion.meeting_point.text != null) {
                meetingPalace.text = args.excursion.meeting_point.text.toString()
            } else {
                meetingLay.isVisible = false
            }


            close.setOnClickListener {
                this@DescriptionFragment.dismiss()
            }
        }
    }

}