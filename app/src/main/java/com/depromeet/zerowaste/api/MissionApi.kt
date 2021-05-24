package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.home.Mission
import com.depromeet.zerowaste.data.user.ReqUpdateUserData
import com.depromeet.zerowaste.data.user.UpdatedUserData
import com.depromeet.zerowaste.data.user.User
import retrofit2.http.*

class MissionApi {

    private interface Api {

        /*no params → 전체 미션 리스트*/
        @GET("/api/missions/")
        suspend fun getMissions(
            @Query("place") place: String,
            @Query("difficulty") difficulty: String,
            @Query("theme") theme: String
        ): List<Mission>
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)
        suspend fun getUserInfo(place: String, difficulty: String, theme: String) =
            client.getMissions(place, difficulty, theme)
    }

}