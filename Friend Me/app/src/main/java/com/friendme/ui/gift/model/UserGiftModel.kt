package com.friendme.ui.gift.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserGiftModel(
    @SerializedName("fromuser") var fromUserGift : String?,
    @SerializedName("namagift") var namaGiftUser : String?,
    @SerializedName("imagegift") var imageGiftUser : String?
) : Parcelable {
    constructor() : this(null, null, null)
}