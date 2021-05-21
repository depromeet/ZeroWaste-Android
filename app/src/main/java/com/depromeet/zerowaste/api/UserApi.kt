package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.user.User
import retrofit2.http.GET
import retrofit2.http.Path

class UserApi {

    private interface Api {

        @GET("/api/users/{id}")
        suspend fun getUserInfo(@Path("id") id: Int): Res<User>
    }

    companion object {
        private val client = RetrofitClient.create(Api::class.java)
        suspend fun getUserInfo(id: Int) = client.getUserInfo(id)
    }

}