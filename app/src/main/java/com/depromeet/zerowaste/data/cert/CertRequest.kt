package com.depromeet.zerowaste.data.cert

import com.depromeet.zerowaste.data.Difficulty
import com.google.gson.annotations.SerializedName

data class CertRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("percieved_difficulty")
    val percievedDifficulty: Difficulty,
    @SerializedName("signed_url_num")
    val signedUrlUum: Int,
)

