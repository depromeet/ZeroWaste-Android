package com.depromeet.zerowaste.data.auth

data class KakaoAuth(
    val kakao_access_token: String,
    val email: String? = null
)
