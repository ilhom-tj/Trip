package tj.ilhom.trip.models.excurse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuidePhotos (

	@SerializedName("thumbnail") val thumbnail : String,
	@SerializedName("medium") val medium : String
):Parcelable