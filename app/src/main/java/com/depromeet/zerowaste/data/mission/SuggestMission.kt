package com.depromeet.zerowaste.data.mission

import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Theme
import com.google.gson.annotations.SerializedName

data class SuggestMission(
    @SerializedName("name")
    var name: String,
    @SerializedName("place")
    var place: Place,
    @SerializedName("theme")
    var theme: List<Theme>,
    @SerializedName("difficulty")
    var difficulty: Difficulty,
    @SerializedName("content")
    var content: String,
    @SerializedName("signed_url_num")
    var signedUrlNum: Int,
    @SerializedName("sentence_for_cheer")
    var sentenceForCheer: String?,
)
