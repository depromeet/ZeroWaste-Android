package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.exam.Data1
import com.depromeet.zerowaste.data.exam.Data4
import retrofit2.http.Body
import retrofit2.http.POST

class ExamApi {

    private interface Api {
        @POST("/exam/post")
        suspend fun post(@Body req: Data1): Data4
    }

    companion object {
        suspend fun post(req: Data1): Data4 = RetrofitClient.create(Api::class.java).post(req)
    }

}