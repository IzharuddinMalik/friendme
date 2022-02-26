package com.friendme.ui.dashboard.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListFeedModel(

    @SerializedName("idfeed") var idFeed : String?,
    @SerializedName("iduser") var idUser : String?,
    @SerializedName("message") var message : String?,
    @SerializedName("imagefeed") var imageFeed : String?,
    @SerializedName("createon") var createOn : String?,
    @SerializedName("likecount") var likeCount : String?,
    @SerializedName("nama") var namaUser : String?,
    @SerializedName("fotoprofile") var fotoProfile : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null)
}