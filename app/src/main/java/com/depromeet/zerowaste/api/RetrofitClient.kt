package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.BuildConfig
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val loggingInterceptor = run {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            else
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            loggingInterceptor
        }

    private val authHeaderInterceptor: (chain: Interceptor.Chain) -> Response = { chain ->
        val request = chain.request().newBuilder().addHeader("Authorization", "jwt ${Share.authToken}").build()
        chain.proceed(request)
    }

    private fun getNoAuthRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build()
    }

    private fun getRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(authHeaderInterceptor).addInterceptor(loggingInterceptor).build())
            .build()
    }

    fun <T> createNoAuth(endPoint: String = Constants.API_SERVER, service: Class<T>): T {
        return getNoAuthRetrofit(endPoint).create(service)
    }

    fun <T> create(service: Class<T>): T {
        return getRetrofit(Constants.API_SERVER).create(service)
    }

    fun <T> create(endPoint: String = Constants.API_SERVER, service: Class<T>): T {
        return getRetrofit(endPoint).create(service)
    }


}