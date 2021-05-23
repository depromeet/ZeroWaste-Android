package com.depromeet.zerowaste.data

import com.google.gson.annotations.SerializedName

data class Res<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("message")
    val message: String,
    @SerializedName("error_code")
    val errorCode: Int
)
