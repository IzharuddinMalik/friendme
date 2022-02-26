package com.friendme.ui.detailroom.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeaveRoomModel(
    @SerializedName("leavemessage") var leaveMessage : String?
) : Parcelable {
    constructor() : this(null)
}