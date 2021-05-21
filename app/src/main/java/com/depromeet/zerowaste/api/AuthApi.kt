package com.depromeet.zerowaste.api


import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.auth.req.KakaoAuth
import com.depromeet.zerowaste.data.auth.req.Refresh
import com.depromeet.zerowaste.data.auth.res.User
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

class AuthApi {

    private interface Api {

        @POST("/api/jwt-auth/kakao/")
        suspend fun getServerTokenWithKakao(@Body req: KakaoAuth): Res<User>

        @POST("/api/jwt-auth/refresh/")
        suspend fun refreshServerToken(@Body req: Refresh): Res<User>
    }

    companion object {
        private val client = RetrofitClient.create(Api::class.java)
        suspend fun getServerTokenWithKakao(req: KakaoAuth) = client.getServerTokenWithKakao(req)
        suspend fun refreshServerToken(req: Refresh) = client.refreshServerToken(req)
    }
}