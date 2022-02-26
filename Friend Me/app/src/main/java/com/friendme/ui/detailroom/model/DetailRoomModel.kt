package com.friendme.ui.detailroom.model

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailRoomModel(
    @SerializedName("namaroom") var namaRoom : String?,
    @SerializedName("managedroom") var managedRoom : String?,
    @SerializedName("username") var usernameRoom : String?,
    @SerializedName("listuser") var listUser : MutableList<ListUserRoomModel>?,
    @SerializedName("leavedate") var leaveDate : String?,
    @SerializedName("joindate") var joinDate : String?,
    @SerializedName("categoryroom") var categoryRoom : String?
) : Parcelable{
    constructor() : this( null, null, null, null, null, null, null)
}