package com.depromeet.zerowaste.data.cert

import com.depromeet.zerowaste.data.Difficulty
import com.google.gson.annotations.SerializedName

data class Certificate(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("mission_id")
    val missionId: Int,
    @SerializedName("img_urls")
    val imgUrls: List<String>,
    @SerializedName("content")
    val content: String,
    @SerializedName("is_public")
    val isPublic: Boolean,
    @SerializedName("percieved_difficulty")
    val percievedDifficulty: Difficulty,
)