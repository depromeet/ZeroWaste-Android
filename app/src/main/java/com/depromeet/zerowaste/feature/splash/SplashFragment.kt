package com.depromeet.zerowaste.feature.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.data.Constants
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.comm.getPreference
import com.depromeet.zerowaste.databinding.FragmentSplashBinding
import com.depromeet.zerowaste.feature.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        viewModel.error.observe(this) {
            showToast(String.format(resources.getString(R.string.exception_default), it.message))
        }
        chkLogin()
    }

    private fun chkLogin() {
        if(Share.authToken.isEmpty()) {
            // 로그인으로 이동
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        } else {
            viewModel.isSuccess.observe(this) { res ->
                if(res) findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
                else showToast(resources.getString(R.string.server_login_fail))
            }
            viewModel.refreshToken()
        }
    }

}