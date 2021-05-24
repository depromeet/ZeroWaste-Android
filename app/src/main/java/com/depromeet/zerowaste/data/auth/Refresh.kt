package com.depromeet.zerowaste.data.auth

import com.google.gson.annotations.SerializedName

data class Refresh(
    @SerializedName("token")
    val token: String
)
