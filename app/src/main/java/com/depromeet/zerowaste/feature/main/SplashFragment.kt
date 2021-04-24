package com.depromeet.zerowaste.feature.main

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun init() {
        viewModel.setTestValue("splash success!!")
        goMain()
    }

    private fun goMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainHomeFragment())
        }, 1000)
    }

}