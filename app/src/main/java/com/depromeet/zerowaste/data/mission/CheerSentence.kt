package com.depromeet.zerowaste.data.mission

import com.google.gson.annotations.SerializedName

data class CheerSentence(
    @SerializedName("sentence_for_cheer")
    val sentenceForCheer: String
)