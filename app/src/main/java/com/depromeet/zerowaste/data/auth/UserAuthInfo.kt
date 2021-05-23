package com.depromeet.zerowaste.data.auth

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserAuthInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("created_atz")
    val createdAt: Date,
    @SerializedName("token")
    val token: String,
    @SerializedName("user_id")
    val userId: Int
)

