package com.depromeet.zerowaste.feature

import android.app.Application
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.depromeet.zerowaste.BuildConfig
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.getPreference
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        init()

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        Log.d("HashKey", Utility.getKeyHash(this))
    }


    private fun init() {
        Share.authToken = getPreference(this).getString(Constants.AUTH_TOKEN, null) ?: ""
    }


}