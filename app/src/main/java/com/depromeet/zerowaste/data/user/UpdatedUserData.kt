package com.depromeet.zerowaste.data.user

import com.google.gson.annotations.SerializedName

data class UpdatedUserData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("level")
    val level: Int,
    @SerializedName("is_notify")
    val isNotify: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("completed_mission_counts")
    val completedMissionCounts: Int,
    @SerializedName("progressing_mission_counts")
    val progressingMissionCounts: Int,
    @SerializedName("token")
    val token: String
) {
    fun getUser(): User {
        return User(id, nickname, level, isNotify, description, completedMissionCounts, progressingMissionCounts)
    }
}
