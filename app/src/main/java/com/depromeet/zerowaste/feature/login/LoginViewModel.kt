package com.depromeet.zerowaste.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.AuthApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.auth.req.KakaoAuth
import com.depromeet.zerowaste.data.auth.req.Refresh
import com.depromeet.zerowaste.data.auth.res.User
import com.google.gson.JsonObject

class LoginViewModel: BaseViewModel() {

    private val _loginResult = MutableLiveData<Res<User>>()
    val loginResult: LiveData<Res<User>> get() = _loginResult

    private val _refresh = MutableLiveData<Res<JsonObject>>()
    val refresh: LiveData<Res<JsonObject>> get() = _refresh

    fun refreshToken() {
        execute({
            AuthApi.refreshServerToken(Refresh(Share.authToken))
        }, _refresh)
    }

    fun getServerTokenWithKakao(kakaoToken: String) {
        execute({
            AuthApi.getServerTokenWithKakao(KakaoAuth(kakaoToken))
        }, _loginResult)
    }

}