package tj.ilhom.trip.models.excurse

data class Price(
    val currency: String,
    val currency_rate: Double,
    val discount: Discount,
    val price_from: Boolean,
    val unit_string: String,
    val value: Double,
    val value_string: String
)