package com.depromeet.zerowaste.api

import retrofit2.http.GET

class CertApi {

    private interface Api {
        @GET("/api/missions/{mission_id}/certifications/")
        suspend fun getMissionCertList()
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)

    }
}