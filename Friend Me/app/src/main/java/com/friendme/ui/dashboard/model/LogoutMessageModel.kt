package com.friendme.ui.dashboard.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogoutMessageModel(
    @SerializedName("logoutmessage") var logoutMessage : String?
) : Parcelable {
    constructor() : this(null)
}