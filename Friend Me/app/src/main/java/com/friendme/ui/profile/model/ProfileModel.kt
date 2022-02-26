package com.friendme.ui.profile.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(

    @SerializedName("nama") var namaUser : String?,
    @SerializedName("username") var username : String?,
    @SerializedName("sex") var sexUser : String?,
    @SerializedName("createdate") var createDate : String?,
    @SerializedName("fotoprofile") var fotoProfile : String?,
    @SerializedName("email") var emailUser : String?,
    @SerializedName("about") var aboutUser : String?,
    @SerializedName("statusmessage") var statusMessage : String?,
    @SerializedName("datebirth") var dateBirth : String?,
    @SerializedName("statusonline") var statusOnline : String?,
    @SerializedName("userbalance") var userBalance : String?,
    @SerializedName("follower") var jumlahFollower : String?,
    @SerializedName("following") var jumlahFollowing : String?,
    @SerializedName("posts") var jumlahPosts : String?,
    @SerializedName("userlevel") var userLevel : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null,null, null, null, null, null, null, null, null, null)
}