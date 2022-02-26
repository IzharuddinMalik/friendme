package com.friendme.ui.searchfriend.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListSearchFriendModel(
    @SerializedName("iduser") var idUser : String?,
    @SerializedName("username") var username : String?,
    @SerializedName("fotoprofile") var fotoProfile : String?
) : Parcelable {
    constructor() : this(null, null, null)
}