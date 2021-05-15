package com.depromeet.zerowaste.feature.main

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun init() {
        goMain()
    }

    private fun goMain() {
        // 코루틴케이스
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            binding.root.post {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
        }
//        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainHomeFragment())
//        }, 1000)
    }

}