package com.depromeet.zerowaste.data.auth.req

data class KakaoAuth(
    val kakao_access_token: String,
    val email: String? = null
)
