package tj.ilhom.trip.ui.descriptionModal

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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import tj.ilhom.trip.R
import tj.ilhom.trip.Utils.CBottomSheetDialogFragment
import tj.ilhom.trip.databinding.DescriptionFragmentBinding

class DescriptionFragment : CBottomSheetDialogFragment() {

    private lateinit var viewModel: DescriptionViewModel
    private lateinit var binding: DescriptionFragmentBinding
    private val args: DescriptionFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        setFullScreen()
    }

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

            if (args.excursion.description != null) {
                val details = parsingHtmlForOrganisationDetails(args.excursion.description.toString())
                description.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(details.descriptionHTML, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(details.descriptionHTML)
                }
                organDetailsBody.text = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(details.organisationHTML, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    android.text.Html.fromHtml(details.organisationHTML)
                }
            }

            Glide.with(requireContext())
                .load(args.excursion.guide.avatar.medium)
                .placeholder(R.drawable.profile)
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

    fun parsingHtmlForOrganisationDetails(html: String)  : DetailModel  {
        var doc: Document? = null
        doc = Jsoup.parse(html)
        val detailModel = DetailModel()
        detailModel.descriptionHTML = doc.getElementsByTag("div").outerHtml()
        detailModel.organisationHTML = doc.getElementsByTag("ul").outerHtml()
        return detailModel
    }

    data class DetailModel(
        var descriptionHTML : String ="",
        var organisationHTML :String =""
    )
}