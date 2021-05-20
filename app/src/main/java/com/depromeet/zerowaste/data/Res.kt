package com.depromeet.zerowaste.data

data class Res<T>(
    val data: T,
    val message: String,
    val error_code: Int
)
