package com.friendme.ui.detailroom.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListUserRoomModel(
    @SerializedName("username") var username : String?
) : Parcelable {
    constructor() : this(null)
}