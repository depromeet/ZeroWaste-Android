package com.depromeet.zerowaste.data.mission

data class TempMission(
    val title: String,
    val userName: String,
    val starCount: Int,
    val doCount: Int,
    val tagList: List<TempMissionTag>,
    val isLike: Boolean
)
