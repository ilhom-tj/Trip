package tj.ilhom.trip.models.city

data class CityResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<City>
)