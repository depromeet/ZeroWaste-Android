package com.depromeet.zerowaste.data.user

data class User(
    val id: Int,
    var nickname: String,
    var level: Int,
    var is_notify: Boolean,
    var description: String,
    var completed_mission_counts: Int,
    var progressing_mission_counts: Int
)