package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagX(
    val id: Int,
    val is_attraction: Boolean,
    val is_auto: Boolean,
    val is_common: Boolean,
    val is_hidden: Boolean,
    val name: String,
    val slug: String
):Parcelable