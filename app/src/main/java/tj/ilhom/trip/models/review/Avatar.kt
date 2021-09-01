package tj.ilhom.trip.models.review

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Avatar(
    val medium: String,
    val small: String
):Parcelable