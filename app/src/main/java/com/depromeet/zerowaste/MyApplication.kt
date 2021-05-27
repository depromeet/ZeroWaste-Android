package com.depromeet.zerowaste

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.depromeet.zerowaste.comm.BaseActivity
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.getPreference
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

object App {
    lateinit var VM: AppViewModel
    val currentBaseActivity: BaseActivity<*>? get() = VM.currentBaseActivity.value
}

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
        Share.authToken = getPreference(this).getString(Constants.AUTH_TOKEN, "") ?: ""
        registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
        App.VM = AppViewModel(this)
    }

    inner class MyActivityLifecycleCallbacks : ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            App.VM.setCurrentBaseActivity(if(activity is BaseActivity<*>) activity else null)
        }
        override fun onActivityStarted(activity: Activity) {
            App.VM.setCurrentBaseActivity(if(activity is BaseActivity<*>) activity else null)
        }
        override fun onActivityResumed(activity: Activity) {
            App.VM.setCurrentBaseActivity(if(activity is BaseActivity<*>) activity else null)
        }
        override fun onActivityPaused(activity: Activity) {
            App.VM.setCurrentBaseActivity(null)
        }
        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }


}