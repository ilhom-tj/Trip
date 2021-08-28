package tj.ilhom.trip.models.excurse

data class Tag(
    val experience_count: Int,
    val extra_header: String,
    val header: String,
    val id: Int,
    val image: ImageX,
    val is_hidden: Boolean,
    val name: String,
    val review_count: Int,
    val slug: String,
    val tag: TagX,
    val title: String,
    val url: String
)