package com.depromeet.zerowaste.data.mission

import com.depromeet.zerowaste.data.ParticipateStatus
import com.google.gson.annotations.SerializedName
import java.util.*

data class StartParticipateData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("mission")
    val mission: Int,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("status")
    val status: ParticipateStatus,
    @SerializedName("start_date")
    val startDate: Date,
    @SerializedName("end_date")
    val endDate: Date,
    @SerializedName("is_cron_checked")
    val isCronChecked: Boolean
)
