package com.depromeet.zerowaste.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.AuthApi
import com.depromeet.zerowaste.api.UserApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.data.auth.KakaoAuth
import com.depromeet.zerowaste.data.auth.Refresh

class LoginViewModel: BaseViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun refreshToken() {
        execute({
            val userAuth = AuthApi.refreshServerToken(Refresh(Share.authToken)).data ?: return@execute false
            Share.authToken = userAuth.token
            val user = UserApi.getUserInfo(userAuth.id).data ?: return@execute false
            Share.user = user
            return@execute true
        }, _isSuccess)
    }

    fun getUserWithKakaoToken(kakaoToken: String) {
        execute({
            val userAuth = AuthApi.getServerTokenWithKakao(KakaoAuth(kakaoToken)).data ?: return@execute false
            Share.authToken = userAuth.token
            val user = UserApi.getUserInfo(userAuth.id).data ?: return@execute false
            Share.user = user
            return@execute true
        }, _isSuccess)
    }

}