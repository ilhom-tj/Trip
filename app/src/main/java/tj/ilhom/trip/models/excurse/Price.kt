package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    val currency: String,
    val currency_rate: Double,
    val discount: Discount,
    val price_from: Boolean,
    val unit_string: String,
    val value: Double,
    val value_string: String
):Parcelable