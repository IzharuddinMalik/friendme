package com.friendme.ui.detailroom.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListChatRoomModel(
    @SerializedName("namauser") var namaUser : String?,
    @SerializedName("message") var message : String?,
    @SerializedName("levelmanagement") var leveManagement : String?,
    @SerializedName("joindate") var joinDate : String?,
    @SerializedName("leavedate") var leaveDate : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null)
}