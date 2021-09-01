package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Excurse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("url") val url: String,
    @SerializedName("is_new") val is_new: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("instant_booking") val instant_booking: Boolean,
    @SerializedName("child_friendly") val child_friendly: Boolean,
    @SerializedName("max_persons") val max_persons: Int,
    @SerializedName("duration") val duration: Double,
    @SerializedName("meeting_point") val meeting_point: MeetingPoint,
    @SerializedName("price") val price: Price,
    @SerializedName("review_count") val review_count: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("city") val city: City,
    @SerializedName("guide") val guide: Guide,
    @SerializedName("tags") val tags: List<Tag>,
    @SerializedName("photos") val photos: List<Photo>,
    @SerializedName("links") val links: Links,
    @SerializedName("status") val status: String,
    @SerializedName("exp_format") val exp_format: Int,
    @SerializedName("movement_type") val movement_type: String,
    @SerializedName("is_visible") val is_visible: Boolean,
    @SerializedName("annotation") val annotation: String?= null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("schedule") val schedule: Schedule,
    @SerializedName("cover_image") val cover_image: String,
    @SerializedName("guide_photos") val guide_photos: List<GuidePhotos>
) : Parcelable