package com.depromeet.zerowaste.data.user

import com.google.gson.annotations.SerializedName

data class ReqUpdateUserData(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("level")
    val level: Int? = null,
    @SerializedName("is_notify")
    val isNotify: Boolean? = null
)
