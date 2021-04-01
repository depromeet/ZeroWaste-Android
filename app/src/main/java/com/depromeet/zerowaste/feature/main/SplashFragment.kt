package com.depromeet.zerowaste.feature.main

import androidx.fragment.app.activityViewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentSplashBinding

class SplashFragment: BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun init() {
        viewModel.setTestValue("splash success!!")
    }

}