package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Avatar(
    val medium: String,
    val small: String
):Parcelable