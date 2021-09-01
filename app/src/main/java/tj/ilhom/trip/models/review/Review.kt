package tj.ilhom.trip.models.review

data class Review(
    val avatar: Avatar,
    val created_on: String,
    val experience: Experience,
    val guide_reply: GuideReply? = null,
    val id: Int,
    val name: String,
    val rating: Int,
    val text: String,
    val traveler_level: Int
)