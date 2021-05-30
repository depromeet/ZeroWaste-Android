package com.depromeet.zerowaste.data.home


import com.google.gson.annotations.SerializedName

data class Mission(
    @SerializedName("banner_img_urls")
    val bannerImgUrls: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Int,
    @SerializedName("place")
    val place: String,
    @SerializedName("sentence_for_cheer")
    val sentenceForCheer: String,
    @SerializedName("signed_url_num")
    val signedUrlNum: Int,
    @SerializedName("theme")
    val theme: String
)