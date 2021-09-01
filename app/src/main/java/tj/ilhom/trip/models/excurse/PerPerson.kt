package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PerPerson(
    @SerializedName("id") val id: Double,
    @SerializedName("title") val title: String,
    @SerializedName("is_default") val is_default: Boolean,
    @SerializedName("value") val value: Double
) : Parcelable
