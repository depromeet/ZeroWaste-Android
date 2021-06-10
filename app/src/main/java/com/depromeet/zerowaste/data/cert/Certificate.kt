package com.depromeet.zerowaste.data.cert

import com.depromeet.zerowaste.data.Difficulty
import com.google.gson.annotations.SerializedName

data class Certificate(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("mission_id")
    val missionId: Int,
    @SerializedName("img_urls")
    val imgUrls: List<String>,
    @SerializedName("content")
    val content: List<String>,
    @SerializedName("isPublic")
    val isPublic: List<String>,
    @SerializedName("percieved_difficulty")
    val percievedDifficulty: Difficulty,
)