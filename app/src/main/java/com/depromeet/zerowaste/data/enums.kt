package com.depromeet.zerowaste.data

import androidx.annotation.IntRange
import androidx.annotation.StringRes
import com.depromeet.zerowaste.R
import com.google.gson.annotations.SerializedName

enum class Difficulty(@IntRange(from=0,to=4) val level: Int) {
    @SerializedName("very_easy")
    VERY_EASY(0),
    @SerializedName("easy")
    EASY(1),
    @SerializedName("medium")
    MEDIUM(2),
    @SerializedName("hard")
    HARD(3),
    @SerializedName("extra_hard")
    EXTRA_HARD(4);
}

enum class Place(@StringRes val textId: Int) {
    ALL(R.string.place_all),
    @SerializedName("etc")
    ETC( R.string.place_etc),
    @SerializedName("kitchen")
    KITCHEN(R.string.place_kitchen),
    @SerializedName("bathroom")
    BATHROOM( R.string.place_bathroom),
    @SerializedName("cafe")
    CAFE(R.string.place_cafe),
    @SerializedName("restaurant")
    RESTAURANT(R.string.place_restaurant),
    @SerializedName("outside")
    OUTSIDE(R.string.place_outside)
}

enum class Theme(@StringRes val textId: Int) {
    @SerializedName("refuse")
    REFUSE(R.string.theme_refuse),
    @SerializedName("reduce")
    REDUCE(R.string.theme_reduce),
    @SerializedName("reuse")
    REUSE(R.string.theme_reuse),
    @SerializedName("recycle")
    RECYCLE(R.string.theme_recycle),
    @SerializedName("rot")
    ROT(R.string.theme_rot)
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