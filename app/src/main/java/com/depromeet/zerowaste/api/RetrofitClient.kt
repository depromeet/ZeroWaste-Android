package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.BuildConfig
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val okHttpBuilder = OkHttpClient.Builder()
        .addInterceptor({
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            else
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            loggingInterceptor
        }.invoke())

    private fun createOkHttpClient(): OkHttpClient {
        val token = Share.token
        if (token.isNotEmpty()) {
            okHttpBuilder.addInterceptor { chain ->
                return@addInterceptor chain.proceed(chain.request().newBuilder().let {
                    it.header("Authorization", "Bearer $token")
                    it.build()
                })
            }
        }
        return okHttpBuilder.build()
    }

    private fun getRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun defaultRetrofit(): Retrofit {
        return getRetrofit(Constants.API_SERVER)
    }

    fun <T> create(service: Class<T>): T {
        return defaultRetrofit().create(service)
    }

    fun <T> create(endPoint: String, service: Class<T>): T {
        return getRetrofit(endPoint).create(service)
    }


}