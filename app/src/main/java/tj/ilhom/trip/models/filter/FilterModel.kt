package tj.ilhom.trip.models.filter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterModel(
    var startPrice: Double? = null,
    var endPrice: Double? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var tripType: MutableList<String> = mutableListOf(),
    var tripMoveType: MutableList<String> = mutableListOf(),
    var tripTagType: MutableList<String> = mutableListOf()
) : Parcelable