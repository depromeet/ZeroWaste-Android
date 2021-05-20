package com.depromeet.zerowaste.feature.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.databinding.FragmentSplashBinding
import com.depromeet.zerowaste.feature.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        viewModel.error.observe(this) {
            // 에러 발생
        }
        chkLogin()
    }

    private fun chkLogin() {
        if(Share.authToken.isEmpty()) {
            // 로그인으로 이동
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        } else {
            viewModel.refresh.observe(this) {
                // 메인으로 이동
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
            viewModel.refreshToken()
        }
    }

}