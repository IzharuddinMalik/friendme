package com.friendme.ui.dashboardadmin.managementuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LevelUserModel(
    @SerializedName("idlevel") var idLevel : String?,
    @SerializedName("leveluser") var levelUser : String?
) : Parcelable {
    constructor() : this(null, null)
}