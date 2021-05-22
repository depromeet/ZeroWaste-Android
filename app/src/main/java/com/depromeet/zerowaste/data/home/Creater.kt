package com.depromeet.zerowaste.data.home


import com.google.gson.annotations.SerializedName

data class Creater(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_notify")
    val isNotify: Boolean,
    @SerializedName("level")
    val level: Int,
    @SerializedName("nickname")
    val nickname: String
)