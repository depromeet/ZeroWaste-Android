package com.depromeet.zerowaste.data.home


import com.google.gson.annotations.SerializedName

data class Mission(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error_code")
    val errorCode: Int
)