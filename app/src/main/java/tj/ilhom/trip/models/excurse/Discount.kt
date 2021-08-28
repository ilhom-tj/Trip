package tj.ilhom.trip.models.excurse

data class Discount(
    val expiration_date: String,
    val expiration_text: String,
    val original_price: Double,
    val value: Double
)