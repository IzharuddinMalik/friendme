package com.friendme.ui.dashboardadmin.managementuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListUserModel(
    @SerializedName("iduser") var idUser : String?,
    @SerializedName("username") var username : String?,
    @SerializedName("fotoprofile") var fotoProfile : String?,
    @SerializedName("idlevelmanagement") var idLevelManagement : String?,
    @SerializedName("levelmanagement") var levelManagement : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null)
}