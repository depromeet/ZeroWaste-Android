package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.auth.KakaoAuth
import com.depromeet.zerowaste.data.auth.Refresh
import com.depromeet.zerowaste.data.auth.UserAuthInfo
import retrofit2.http.Body
import retrofit2.http.POST

class AuthApi {

    private interface Api {

        @POST("/api/jwt-auth/kakao/")
        suspend fun getServerTokenWithKakao(@Body req: KakaoAuth): Res<UserAuthInfo>

        @POST("/api/jwt-auth/refresh/")
        suspend fun refreshServerToken(@Body req: Refresh): Res<UserAuthInfo>
    }

    companion object {
        private val client get() = RetrofitClient.createNoAuth(service = Api::class.java)
        suspend fun getServerTokenWithKakao(req: KakaoAuth) = client.getServerTokenWithKakao(req)
        suspend fun refreshServerToken(req: Refresh) = client.refreshServerToken(req)
    }
}