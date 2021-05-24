package com.depromeet.zerowaste.data.auth

import com.google.gson.annotations.SerializedName

data class KakaoAuth(
    @SerializedName("kakao_access_token")
    val kakaoAccessToken: String,
    @SerializedName("email")
    val email: String? = null
)
