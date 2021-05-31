package com.depromeet.zerowaste.data

import com.google.gson.annotations.SerializedName

enum class Difficulty(val value: String) {
    @SerializedName("very_easy")
    VERY_EASY("very_easy"),
    @SerializedName("easy")
    EASY("easy"),
    @SerializedName("medium")
    MEDIUM("medium"),
    @SerializedName("hard")
    HARD("hard"),
    @SerializedName("extra_hard")
    EXTRA_HARD("extra_hard")
}

enum class Place(val value: String) {
    @SerializedName("etc")
    ETC("etc"),
    @SerializedName("kitchen")
    KITCHEN("kitchen"),
    @SerializedName("bathroom")
    BATHROOM("bathroom"),
    @SerializedName("cafe")
    CAFE("cafe"),
    @SerializedName("restaurant")
    RESTAURANT("restaurant"),
    @SerializedName("outside")
    OUTSIDE("outside")
}

enum class Theme(val value: String) {
    @SerializedName("refuse")
    REFUSE("refuse"),
    @SerializedName("reduce")
    REDUCE("reduce"),
    @SerializedName("reuse")
    REUSE("reuse"),
    @SerializedName("recycle")
    RECYCLE("recycle"),
    @SerializedName("rot")
    ROT("rot")
}

enum class ParticipateStatus(val value: String) {
    @SerializedName("ready")
    READY("ready"),
    @SerializedName("participated")
    PARTICIPATED("participated"),
    @SerializedName("success")
    SUCCESS("success"),
    @SerializedName("failure")
    FAILURE("failure")
}