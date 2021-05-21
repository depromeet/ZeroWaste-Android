package com.depromeet.zerowaste.feature.login

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        viewModel.error.observe(this) {
            showToast(String.format(resources.getString(R.string.exception_default), it.message))
        }
        binding.fragment = this
    }

    fun kakaoLoginClick() {
        val kakaoAuthLambda: (token: OAuthToken?, error: Throwable?) -> Unit = { token, e ->
            e?.printStackTrace()
            token?.accessToken?.also {
                getServerToken(it)
            } ?: showToast(resources.getString(R.string.kakao_login_fail))
        }
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoAuthLambda)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoAuthLambda)
        }
    }

    private fun getServerToken(kakaoToken: String) {
        viewModel.isSuccess.observe(this) { res ->
            if(res) findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            else showToast(resources.getString(R.string.server_login_fail))
        }
        viewModel.getUserWithKakaoToken(kakaoToken)
    }

}