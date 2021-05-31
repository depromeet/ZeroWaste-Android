package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.Mission
import retrofit2.http.GET
import retrofit2.http.Query

class MissionApi {

    private interface Api {

        /*no params → 전체 미션 리스트*/
        @GET("/api/missions/")
        suspend fun getMissions(
            @Query("place") place: Place?,
            @Query("difficulty") difficulty: Difficulty?,
            @Query("theme") theme: Theme?
        ): Res<List<Mission>>
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)
        suspend fun getMissions(place: Place? = null, difficulty: Difficulty? = null, theme: Theme? = null) =
            client.getMissions(place, difficulty, theme)
    }

}