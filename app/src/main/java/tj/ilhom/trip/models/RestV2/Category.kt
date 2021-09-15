package tj.ilhom.trip.models.RestV2

data class CategoryResponse (val code: Int, val data: List<Category>)
data class Category (val id: Int, val name: String)