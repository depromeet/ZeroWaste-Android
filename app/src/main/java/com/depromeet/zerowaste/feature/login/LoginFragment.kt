package com.depromeet.zerowaste.feature.login

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.getPreference
import com.depromeet.zerowaste.databinding.FragmentLoginBinding
import com.kakao.sdk.user.UserApiClient

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun init() {
        binding.fragment = this
    }

    fun kakaoLoginClick() {
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                Log.e("tttt", token?.accessToken.toString())
                error?.printStackTrace()
                token?.accessToken?.also {
                    Share.authToken = it
                    getPreference(requireContext()).edit().putString(Constants.AUTH_TOKEN, it).apply()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                Log.e("tttt", token?.accessToken.toString())
                error?.printStackTrace()
                token?.accessToken?.also {
                    Share.authToken = it
                    getPreference(requireContext()).edit().putString(Constants.AUTH_TOKEN, it).apply()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                }
            }
        }

    }

}