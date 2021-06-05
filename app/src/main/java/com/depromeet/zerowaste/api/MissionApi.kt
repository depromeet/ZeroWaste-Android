package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.*
import com.depromeet.zerowaste.data.mission.Mission
import retrofit2.http.*

class MissionApi {

    private interface Api {

        /*no params → 전체 미션 리스트*/
        @GET("/api/missions/")
        suspend fun getMissions(
            @Query("place") place: Place?,
            @Query("difficulty") difficulty: Difficulty?,
            @Query("theme") theme: Theme?,
            @Query("ordering") ordering: Ordering?
        ): Res<List<Mission>>

        @POST("/api/missions/{id}/like")
        suspend fun likeMission(
            @Path("id") missionId: Int
        ): Res<Any>

        @DELETE("/api/missions/{id}/dislike")
        suspend fun dislikeMission(
            @Path("id") missionId: Int
        ): Res<Any>
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)
        suspend fun getMissions(place: Place? = null, difficulty: Difficulty? = null, theme: Theme? = null, ordering: Ordering? = null) =
            client.getMissions(place, difficulty, theme, ordering)
        suspend fun likeMission(missionId: Int) = client.likeMission(missionId)
        suspend fun dislikeMission(missionId: Int) = client.dislikeMission(missionId)
    }

}