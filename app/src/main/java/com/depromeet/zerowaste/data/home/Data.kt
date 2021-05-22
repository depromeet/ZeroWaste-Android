package com.depromeet.zerowaste.data.home


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("content")
    val content: String,
    @SerializedName("creater")
    val creater: Creater,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("icon_img_url")
    val iconImgUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_img_url")
    val logoImgUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("place")
    val place: String,
    @SerializedName("theme")
    val theme: List<String>
)