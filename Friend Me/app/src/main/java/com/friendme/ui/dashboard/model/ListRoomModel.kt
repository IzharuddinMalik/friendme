package com.friendme.ui.dashboard.model

import android.accessibilityservice.GestureDescription
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListRoomModel(
    @SerializedName("idroom") var idRoom : String?,
    @SerializedName("namaroom") var namaRoom : String?,
    @SerializedName("lastchat") var lastChat : String?,
    @SerializedName("jumlahchat") var jumlahChat : String?,
    @SerializedName("jumlahanggota") var jumlahAnggota : String?,
    @SerializedName("quotaroom") var quotaRoom : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null)
}