package com.friendme.ui.gift.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GiftModel(
    @SerializedName("idgift") var idGift : String?,
    @SerializedName("namagift") var namaGift : String?,
    @SerializedName("imagegift") var imageGift : String?,
    @SerializedName("idrgift") var idrGift : String?
) : Parcelable {
    constructor() : this(null, null, null, null)
}