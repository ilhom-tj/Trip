package tj.ilhom.trip.models.RestV2

import com.google.gson.annotations.SerializedName

data class ReviewV2(
    val review: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("excursions_id")
    val excursionId: Int
    )