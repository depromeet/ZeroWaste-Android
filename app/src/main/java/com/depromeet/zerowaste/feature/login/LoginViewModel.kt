package com.depromeet.zerowaste.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.AuthApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.data.Res
import com.depromeet.zerowaste.data.auth.KakaoAuth
import com.depromeet.zerowaste.data.auth.Refresh
import com.depromeet.zerowaste.data.auth.User

class LoginViewModel: BaseViewModel() {

    private val _loginResult = MutableLiveData<Res<User>>()
    val loginResult: LiveData<Res<User>> get() = _loginResult

    private val _refresh = MutableLiveData<Res<User>>()
    val refresh: LiveData<Res<User>> get() = _refresh

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