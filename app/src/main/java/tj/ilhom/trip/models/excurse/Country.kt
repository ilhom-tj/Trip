package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val currency: String,
    val experience_count: Int,
    val id: Int,
    val in_obj_phrase: String,
    val name_en: String,
    val name_ru: String,
    val region: String,
    val url: String
):Parcelable