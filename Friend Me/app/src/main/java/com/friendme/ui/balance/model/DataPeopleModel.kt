package com.friendme.ui.balance.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPeopleModel(
    @SerializedName("admin") var adminPeople : MutableList<PeopleModel>?,
    @SerializedName("merchant") var merchantPeople : MutableList<PeopleModel>?,
    @SerializedName("mentor") var mentorPeople : MutableList<PeopleModel>?,
    @SerializedName("staff") var staffPeople : MutableList<PeopleModel>?
) : Parcelable {
    constructor() : this(null, null, null, null)
}