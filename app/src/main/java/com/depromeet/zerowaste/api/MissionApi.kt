package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.*
import com.depromeet.zerowaste.data.comn.SignedUrlListAndId
import com.depromeet.zerowaste.data.mission.CheerSentence
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.data.mission.StartParticipateData
import com.depromeet.zerowaste.data.mission.SuggestMission
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

        @GET("/api/missions/{id}/")
        suspend fun getMission(
            @Path("id") missionId: Int
        ): Res<Mission>

        @POST("/api/missions/")
        suspend fun suggestNewMission(
            @Body suggestMission: SuggestMission
        ): Res<SignedUrlListAndId>

        @PATCH("/api/missions/{id}/")
        suspend fun addCheerSentence(
            @Path("id") missionId: Int,
            @Body cheerSentence: CheerSentence
        ): Res<Mission>

        @POST("/api/missions/{id}/like")
        suspend fun likeMission(
            @Path("id") missionId: Int
        ): Res<Any>

        @DELETE("/api/missions/{id}/dislike")
        suspend fun dislikeMission(
            @Path("id") missionId: Int
        ): Res<Any>

        @POST("/api/missions/{id}/participations/")
        suspend fun participateMission(
            @Path("id") missionId: Int
        ): Res<StartParticipateData>

        @PATCH("/api/missions/{missionId}/participations/{participationId}/")
        suspend fun participatePatchMission(
            @Path("missionId") missionId: Int,
            @Path("participationId") participationId: Int
        ): Res<StartParticipateData>
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)
        suspend fun getMissions(place: Place? = null, difficulty: Difficulty? = null, theme: Theme? = null, ordering: Ordering? = null) =
            client.getMissions(place, difficulty, theme, ordering)
        suspend fun getMission(missionId: Int) = client.getMission(missionId)
        suspend fun likeMission(missionId: Int) = client.likeMission(missionId)
        suspend fun dislikeMission(missionId: Int) = client.dislikeMission(missionId)
        suspend fun participateMission(missionId: Int) = client.participateMission(missionId)
        suspend fun participatePatchMission(missionId: Int, participationId: Int) = client.participatePatchMission(missionId, participationId)
        suspend fun suggestNewMission(suggestMission: SuggestMission) = client.suggestNewMission(suggestMission)
        suspend fun addCheerSentence(missionId: Int, cheerSentence: CheerSentence) = client.addCheerSentence(missionId, cheerSentence)

    }

}