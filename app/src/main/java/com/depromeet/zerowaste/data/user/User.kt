package com.depromeet.zerowaste.data.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("level")
    var level: Int,
    @SerializedName("is_notify")
    var isNotify: Boolean,
    @SerializedName("description")
    var description: String,
    @SerializedName("completed_mission_counts")
    var completedMissionCounts: Int,
    @SerializedName("progressing_mission_counts")
    var progressingMissionCounts: Int
)