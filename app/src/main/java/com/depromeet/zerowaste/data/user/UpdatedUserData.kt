package com.depromeet.zerowaste.data.user

data class UpdatedUserData(
    val id: Int,
    var nickname: String,
    var level: Int,
    var is_notify: Boolean,
    var description: String,
    var completed_mission_counts: Int,
    var progressing_mission_counts: Int,
    val token: String
) {
    fun getUser(): User {
        return User(id, nickname, level, is_notify, description, completed_mission_counts, progressing_mission_counts)
    }
}
