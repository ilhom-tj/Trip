package tj.ilhom.trip.models.excurse

data class Excurse(
    val child_friendly: Boolean,
    val city: City,
    val duration: Double,
    val exp_format: Int,
    val guide: Guide,
    val id: Int,
    val instant_booking: Boolean,
    val is_new: Boolean,
    val is_visible: Boolean,
    val links: LinksX,
    val max_persons: Int,
    val meeting_point: MeetingPoint,
    val movement_type: String,
    val photos: List<Photo>,
    val popularity: Int,
    val price: Price,
    val rating: Double,
    val review_count: Int,
    val status: String,
    val tagline: String,
    val tags: List<Tag>,
    val title: String,
    val type: String,
    val url: String
)