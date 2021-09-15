package tj.ilhom.trip.Utils

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class CBottomSheetDialogFragment : BottomSheetDialogFragment() {
    //wanna get the bottomSheetDialog
    protected lateinit var dialog : BottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    //set the behavior here
    fun setFullScreen(){
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}