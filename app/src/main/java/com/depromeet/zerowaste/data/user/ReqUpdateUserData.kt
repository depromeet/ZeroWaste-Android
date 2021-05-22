package com.depromeet.zerowaste.data.user

data class ReqUpdateUserData(
    val nickname: String,
    val level: Int? = null,
    val is_notify: Boolean? = null
)
