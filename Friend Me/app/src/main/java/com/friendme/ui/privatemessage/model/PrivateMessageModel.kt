package com.friendme.ui.privatemessage.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrivateMessageModel(

    @SerializedName("message") var message : String?,
    @SerializedName("namapengirim") var namaPengirim : String?,
) : Parcelable {
    constructor() : this(null, null)
}