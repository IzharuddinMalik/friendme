package com.friendme.ui.dashboard.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatusFriendModel(
    @SerializedName("iduser") var idUser : String?,
    @SerializedName("username") var username : String?,
    @SerializedName("fotoprofile") var fotoProfile : String?,
    @SerializedName("statusmessage") var statusMessage : String?,
    @SerializedName("statusonline") var statusOnline : String?,
    @SerializedName("logouton") var logouton : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null)
}