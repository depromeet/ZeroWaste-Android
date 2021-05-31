package com.depromeet.zerowaste.data.mission

import com.depromeet.zerowaste.data.ParticipateStatus
import com.google.gson.annotations.SerializedName
import java.util.*

data class Participation(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: ParticipateStatus,
    @SerializedName("start_date")
    val startDate: Date,
    @SerializedName("end_date")
    val endDate: Date
)