package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule (

	@SerializedName("text") val text : String
):Parcelable