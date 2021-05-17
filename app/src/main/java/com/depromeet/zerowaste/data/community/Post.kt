package com.depromeet.zerowaste.data.community

import com.depromeet.zerowaste.data.mission.Tag
import java.util.*

data class Post(
    val postId: String = "",
    val postDate: Date = Date(),

    val user: String = "",
    val userName: String = "",
    val userBadge: String = "",
    val userMissionCount: Int = 0,

    val likeCount: Int,
    val isMyLike: Boolean,
    val tag: Tag,
    val content: String,
    val photos: List<String>,
    val difficulty: Int = 0,

    val missionId: String = "",
    val missionName: String,
    val missionCode: String = "",
)