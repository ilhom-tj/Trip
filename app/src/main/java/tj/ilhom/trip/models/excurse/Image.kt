package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val cover: String,
    val thumbnail: String
):Parcelable