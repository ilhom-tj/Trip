package tj.ilhom.trip.models.review


data class ReviewResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Review>
)