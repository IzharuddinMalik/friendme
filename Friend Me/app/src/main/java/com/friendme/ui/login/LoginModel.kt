package com.friendme.ui.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModel(

    @SerializedName("iduser") var idUser : String?,
    @SerializedName("levelmanagement") var levelManagement : String?

) : Parcelable {
    constructor() : this(null, null)
}