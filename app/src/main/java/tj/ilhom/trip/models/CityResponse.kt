package tj.ilhom.trip.models

data class CityResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<City>
)