package com.friendme.ui.dashboard.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JoinRoomModel(
    @SerializedName("namaroom") var namaRoom : String?
) : Parcelable {
    constructor() : this(null)
}