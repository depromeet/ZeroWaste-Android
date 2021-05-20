package com.depromeet.zerowaste.feature.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun init() {
        viewModel.error.observe(this) {
            // 에러 발생
        }
        chkLogin()
    }

    private fun chkLogin() {
        if(Share.authToken.isEmpty()) {
            goLogin()
        } else {
            viewModel.refresh.observe(this) {
                goMain()
            }
            viewModel.refreshToken()
        }
    }

    private fun goLogin() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }

    private fun goMain() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())

        // 코루틴케이스
//        lifecycleScope.launch(Dispatchers.IO) {
//            delay(1000)
//            binding.root.post {
//                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
//            }
//        }
//        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainHomeFragment())
//        }, 1000)
    }

}