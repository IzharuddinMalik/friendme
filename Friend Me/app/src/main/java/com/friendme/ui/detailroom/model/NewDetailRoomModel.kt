package com.friendme.ui.detailroom.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewDetailRoomModel(
    @SerializedName("idroom") var idRoom : String?,
    @SerializedName("namaroom") var namaRoom : String?
) : Parcelable {
    constructor() : this(null, null)
}