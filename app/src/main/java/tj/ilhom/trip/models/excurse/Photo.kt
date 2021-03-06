package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val medium: String,
    val thumbnail: String
):Parcelable