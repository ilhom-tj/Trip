package tj.ilhom.trip.Utils

import android.graphics.Color
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

object BackgroundChanger {
    fun setWindowTransparent(dialog : BottomSheetDialogFragment){
        dialog.requireView().setBackgroundColor(Color.TRANSPARENT)
    }

}