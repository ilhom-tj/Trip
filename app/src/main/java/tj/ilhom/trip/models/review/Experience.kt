package tj.ilhom.trip.models.review

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Experience(
    val id: Int,
    val is_visible: Boolean,
    val title: String,
    val url: String
):Parcelable