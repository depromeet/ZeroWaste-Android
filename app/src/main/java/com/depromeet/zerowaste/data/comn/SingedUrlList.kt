package com.depromeet.zerowaste.data.comn

import com.google.gson.annotations.SerializedName

data class SingedUrlList(
    @SerializedName("signed_url_list")
    val signedUrlList: List<String>
)
