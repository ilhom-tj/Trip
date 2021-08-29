package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinksX(
    val reviews: String,
    val schedule: String
):Parcelable