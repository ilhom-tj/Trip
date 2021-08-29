package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val ascii_name: String,
    val country: Country,
    val experience_count: Int,
    val from_obj_phrase: String,
    val guides_count: Int,
    val iata: String,
    val id: Int,
    val image: Image,
    val in_obj_phrase: String,
    val name_en: String,
    val name_prepositional: String,
    val name_ru: String,
    val url: String,
    val utc_offset: Double
):Parcelable