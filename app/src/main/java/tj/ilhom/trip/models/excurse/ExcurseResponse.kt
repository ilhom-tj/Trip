package tj.ilhom.trip.models.excurse

data class ExcurseResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Excurse>
)