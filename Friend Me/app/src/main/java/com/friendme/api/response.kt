package com.friendme.api

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("success") var success : String,
    @SerializedName("message") var message : String,
    @SerializedName("data") var data : T
)

data class WrappedListResponse<T>(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("data") var data : List<T>
)

data class AnotherResponse(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String
)