package com.friendme.ui.balance.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BalanceUserModel(

    @SerializedName("titlebalance") var titleBalance : String?,
    @SerializedName("balance") var balance : String?,
    @SerializedName("datebalance") var dateBalance : String?
) : Parcelable {
    constructor() : this(null, null, null)
}