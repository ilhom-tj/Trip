package tj.ilhom.trip.ui.excurseFilter.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import tj.ilhom.trip.R
import tj.ilhom.trip.databinding.DialogCalendarBinding

class CalendarDialog : DialogFragment(R.layout.dialog_calendar) {

    private var binding: DialogCalendarBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCalendarBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun getTheme() = R.style.CalendarDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ResourcesCompat.getFont(requireContext(), R.font.sf_ui)?.let {
            binding?.calendar?.setFonts(
                it
            )
        }

        binding?.calendar?.setWeekOffset(1)

        binding?.reset?.setOnClickListener {
            binding?.calendar?.resetAllSelectedViews()
        }
    }

}