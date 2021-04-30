package com.depromeet.zerowaste.feature

import android.app.Application
import android.util.Log
import com.depromeet.zerowaste.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        Log.d("HashKey", Utility.getKeyHash(this))
    }
}