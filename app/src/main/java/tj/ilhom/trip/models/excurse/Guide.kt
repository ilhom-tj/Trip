package tj.ilhom.trip.models.excurse

data class Guide(
    val avatar: Avatar,
    val avg_reaction_delay: Int,
    val first_name: String,
    val guide_type: String,
    val id: Int,
    val links: Links,
    val rating: Double,
    val review_count: Int,
    val url: String
)