package com.depromeet.zerowaste.data.mission

import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.user.User
import com.google.gson.annotations.SerializedName

data class Mission(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("theme")
    val theme: List<Theme>,
    @SerializedName("difficulty")
    val difficulty: Difficulty,
    @SerializedName("banner_img_urls")
    val bannerImgUrls: List<String>,
    @SerializedName("content")
    val content: String,
    @SerializedName("sentence_for_cheer")
    val sentenceForCheer: String,
    @SerializedName("creater")
    val creater: User,
    @SerializedName("participation")
    val participation: Participation,
    @SerializedName("is_liked")
    val isLiked: Boolean
)