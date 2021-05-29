package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.home.Mission
import retrofit2.http.GET
import retrofit2.http.Query

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
        suspend fun getMissions(place: String, difficulty: String, theme: String) =
            client.getMissions(place, difficulty, theme)
    }

}