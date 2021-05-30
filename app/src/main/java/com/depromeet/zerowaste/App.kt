package com.depromeet.zerowaste

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseActivity
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.getPreference
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var appViewModel: AppViewModel
        private val VM get() = appViewModel
        private fun setAppViewModel(appViewModel: AppViewModel) { if(!this::appViewModel.isInitialized) this.appViewModel = appViewModel }
        private val currentBaseActivity: BaseActivity<*>? get() = VM.currentBaseActivity.value

        fun startLoad() { currentBaseActivity?.startLoad() }
        fun finishLoad() { currentBaseActivity?.finishLoad() }
        fun bottomSheet(title: String, contents: List<Pair<Int,String>>, selectedId: Int? = null, onSelect: (Int) -> Unit) { currentBaseActivity?.bottomSheet(title, contents, selectedId, onSelect) }
        fun <V: ViewDataBinding> dialog(@LayoutRes layoutId: Int, widthDP: Float? = null, heightDP: Float? = null, onActive: (V) -> Unit) { currentBaseActivity?.dialog(layoutId, widthDP, heightDP, onActive) }
    }

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        init()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        Log.d("HashKey", Utility.getKeyHash(this))
    }

    private fun init() {
        setAppViewModel(AppViewModel(this))
        Share.authToken = getPreference(this).getString(Constants.AUTH_TOKEN, "") ?: ""
        registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
    }

    inner class MyActivityLifecycleCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            if(activity is BaseActivity<*>) VM.setCurrentBaseActivity(activity)
        }
        override fun onActivityStarted(activity: Activity) {
            if(activity is BaseActivity<*>) VM.setCurrentBaseActivity(activity)
        }
        override fun onActivityResumed(activity: Activity) {
            if(activity is BaseActivity<*>) VM.setCurrentBaseActivity(activity)
        }
        override fun onActivityPaused(activity: Activity) {
            if(activity is BaseActivity<*>) VM.setCurrentBaseActivity(null)
        }
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivityDestroyed(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    }
}

class AppViewModel(application: App): AndroidViewModel(application) {

    private val _currentBaseActivity =  MutableLiveData<BaseActivity<*>?>()
    val currentBaseActivity: LiveData<BaseActivity<*>?> get() = _currentBaseActivity

    fun setCurrentBaseActivity(activity: BaseActivity<*>?) {
        _currentBaseActivity.postValue(activity)
    }
}