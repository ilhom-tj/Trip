package tj.ilhom.trip.models.RestV2

data class V2Response<T> (val code: Int, val data: List<T>)
data class CategoryV2 (val id: Int, val name: String)