package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    val experience_count: Int,
    val extra_header: String,
    val header: String,
    val id: Int,
    val image: Image,
    val is_hidden: Boolean,
    val name: String,
    val review_count: Int,
    val slug: String,
    val tag: Tag,
    val title: String,
    val url: String
):Parcelable