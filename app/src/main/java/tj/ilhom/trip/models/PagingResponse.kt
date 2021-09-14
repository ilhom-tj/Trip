package tj.ilhom.trip.models


data class PagingResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    var results: List<T>  = emptyList()
)