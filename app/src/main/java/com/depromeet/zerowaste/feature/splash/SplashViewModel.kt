package com.depromeet.zerowaste.feature.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.AuthApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.auth.Refresh
import com.google.gson.JsonObject

class SplashViewModel: BaseViewModel() {

    private val _refresh = MutableLiveData<Res<JsonObject>>()
    val refresh: LiveData<Res<JsonObject>> get() = _refresh

    fun refreshToken() {
        execute({
            AuthApi.refreshServerToken(Refresh(Share.authToken))
        }, _refresh)
    }
}