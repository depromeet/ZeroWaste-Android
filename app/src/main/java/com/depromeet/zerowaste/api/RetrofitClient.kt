package com.depromeet.zerowaste.api

import com.depromeet.zerowaste.BuildConfig
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

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

    private val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX").create()

    val uploadClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private fun getNoAuthRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(EnumConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build()
    }

    private fun getRetrofit(endPoint: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint)
            .addConverterFactory(EnumConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
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

class EnumConverterFactory : Converter.Factory() {

    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<Enum<*>, String>? =
        if (type is Class<*> && type.isEnum) {
            Converter { enum ->
                try {
                    enum.javaClass.getField(enum.name)
                        .getAnnotation(SerializedName::class.java)?.value
                } catch (exception: Exception) {
                    null
                }
            }
        } else {
            null
        }
}