package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.cert.CertRequest
import com.depromeet.zerowaste.data.cert.Certificate
import com.depromeet.zerowaste.data.comn.SingedUrlList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class CertApi {

    private interface Api {
        @POST("/api/missions/{id}/certifications/")
        suspend fun certificateMission(
            @Path("id") missionId: Int,
            @Body certRequest: CertRequest
        ): Res<SingedUrlList>
    }

    companion object {
        private val client get() = RetrofitClient.create(Api::class.java)
        suspend fun certificateMission(missionId: Int, certRequest: CertRequest) = client.certificateMission(missionId, certRequest)
    }
}