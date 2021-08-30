package tj.ilhom.trip.ui.excursion.descriptionModal

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            description.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(args.excursion.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(args.excursion.description)
            }
            Glide.with(requireActivity()).load(args.excursion.guide.avatar.medium)
                .into(guideAvatar)
            guideName.text = args.excursion.guide.first_name
            guideDescription.text = args.excursion.guide.description

        }
    }

}