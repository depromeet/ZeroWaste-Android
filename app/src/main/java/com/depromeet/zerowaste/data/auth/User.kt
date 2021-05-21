package com.depromeet.zerowaste.data.auth

import java.util.*

data class User(
    val id: Int,
    val identifier: String,
    val email: String?,
    val created_at: Date,
    val token: String,
    val user_id: Int
)