package tj.ilhom.trip.ui.excursion.share

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tj.ilhom.trip.R
import tj.ilhom.trip.databinding.FragmentShareBinding


class ShareFragment : BottomSheetDialogFragment() {

    private val args: ShareFragmentArgs by navArgs()
    private lateinit var binding: FragmentShareBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = args.excursion.title
        val movementType = when (args.excursion.movement_type) {
                "foot" -> {
                    "Пешком"
                }
                "car" -> {
                    "На машине"
                }
                "bicycle" -> {
                    "На велосипеде"
                }
                "bus" -> {
                    "На автобусе"
                }
                "motorcycle" -> {
                    "На мотоцикле"
                }
                "watership" -> {
                    "На кораблике"
                }
                else -> {
                    "Другое"
                }
            }

        binding.body.text = "$movementType ${args.excursion.duration} часа"
        binding.close.setOnClickListener {
            this.dismiss()
        }
        binding.shareLink.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT, args.excursion.url)
            startActivity(Intent.createChooser(share, "Share link!"))
        }
    }
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

}