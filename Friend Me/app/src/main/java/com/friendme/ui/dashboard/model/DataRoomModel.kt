package com.friendme.ui.dashboard.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataRoomModel(
    @SerializedName("official") var official : MutableList<ListRoomModel>?,
    @SerializedName("favourites") var favourites : MutableList<ListRoomModel>?,
    @SerializedName("playgames") var playGames : MutableList<ListRoomModel>?,
    @SerializedName("recentroom") var recentRoom : MutableList<ListRoomModel>?,
    @SerializedName("random") var random : MutableList<ListRoomModel>?
) : Parcelable {
    constructor() : this(null, null, null, null, null)
}