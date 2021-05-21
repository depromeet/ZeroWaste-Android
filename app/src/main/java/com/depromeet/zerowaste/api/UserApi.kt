package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.user.UpdateUserData
import com.depromeet.zerowaste.data.user.User
import retrofit2.http.*

class UserApi {

    private interface Api {

        @GET("/api/users/{id}/")
        suspend fun getUserInfo(@Path("id") id: Int): Res<User>

        @GET("/api/users/double_check")
        suspend fun checkNickName(@Query("nickname") nickname: String): Res<Any>

        @PATCH("/api/users/{id}/")
        suspend fun updateUserInfo(@Path("id") id: Int, @Body req: UpdateUserData): Res<User>
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)
        suspend fun getUserInfo(id: Int) = client.getUserInfo(id)
        suspend fun checkNickName(nickname: String) = client.checkNickName(nickname)
        suspend fun updateUserInfo(id: Int, req: UpdateUserData) = client.updateUserInfo(id, req)
    }

}