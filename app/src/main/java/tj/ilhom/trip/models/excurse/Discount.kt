package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Discount(
    val expiration_date: String,
    val expiration_text: String,
    val original_price: Double,
    val value: Double
):Parcelable