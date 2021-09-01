package tj.ilhom.trip.models.review

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuideReply(
    val text : String,
    val name : String,
    val avatar : Avatar
):Parcelable