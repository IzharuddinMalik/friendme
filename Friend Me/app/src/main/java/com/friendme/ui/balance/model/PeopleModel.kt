package com.friendme.ui.balance.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeopleModel(
    @SerializedName("iduser") var idUserPeople : String?,
    @SerializedName("username") var usernamePeople : String?,
    @SerializedName("fotoprofile") var fotoProfilePeople : String?,
    @SerializedName("statusonline") var statusOnlinePeople : String?,
    @SerializedName("userlevel") var userLevelPeople : String?,
    @SerializedName("sex") var userSex : String?,
    @SerializedName("levelmanagement") var levelManagement : String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null)
}