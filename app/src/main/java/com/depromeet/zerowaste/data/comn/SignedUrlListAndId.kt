package com.depromeet.zerowaste.data.comn

import com.google.gson.annotations.SerializedName

data class SignedUrlListAndId(
    @SerializedName("id")
    val id: Int,
    @SerializedName("signed_url_list")
    val signedUrlList: List<String>
)
