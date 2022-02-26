package com.friendme.ui.privatemessage.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrivateMessageRoomModel(
    @SerializedName("username") var username : String?,
    @SerializedName("statusmessage") var statusMessage : String?,
    @SerializedName("fotoprofile") var fotoProfile : String?,
    @SerializedName("statusonline") var statusOnline : String?,
    @SerializedName("listchat") var listChat : MutableList<PrivateMessageModel>?
) : Parcelable {
    constructor() : this(null, null, null, null, null)
}