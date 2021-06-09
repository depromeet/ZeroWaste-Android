package com.depromeet.zerowaste.data.cert

import com.google.gson.annotations.SerializedName

data class Certificate(
    @SerializedName("owner")
    val id: Int,
    @SerializedName("owner")
    val title: String,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("mission_id")
    val missionId: Int,
)
