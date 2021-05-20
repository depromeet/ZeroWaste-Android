package com.depromeet.zerowaste.feature.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.getPreference
import com.depromeet.zerowaste.databinding.FragmentLoginBinding
import com.kakao.sdk.user.UserApiClient

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        viewModel.error.observe(this) {
            // 에러처리
        }
        binding.fragment = this
    }

    fun kakaoLoginClick() {
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                token?.accessToken?.also {
                    getServerToken(it)
                }
            }
        } else {
            UserApiClient.instance.me { user, error ->
                user?.kakaoAccount?.email
            }
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                token?.accessToken?.also {
                    getServerToken(it)
                }
            }
        }
    }

    private fun getServerToken(kakaoToken: String) {
        viewModel.loginResult.observe(this) { res ->
            res.data?.also { user ->
                Share.authToken = user.token
                getPreference(requireContext()).edit().putString(Constants.AUTH_TOKEN, user.token).apply()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        }
        viewModel.getServerTokenWithKakao(kakaoToken)
    }

}