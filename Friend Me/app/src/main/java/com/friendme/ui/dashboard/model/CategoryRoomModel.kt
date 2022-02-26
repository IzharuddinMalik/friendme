package com.friendme.ui.dashboard.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryRoomModel(

    @SerializedName("idcategoryroom") var idCategoryRoom : String?,
    @SerializedName("categoryroom") var categoryRoom : String?
) : Parcelable {
    constructor() : this(null, null)
}