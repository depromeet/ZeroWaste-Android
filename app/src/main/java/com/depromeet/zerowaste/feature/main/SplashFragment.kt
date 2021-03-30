package com.depromeet.zerowaste.feature.main

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentSplashBinding

class SplashFragment: BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: MainViewModel by viewModels({requireActivity()})

    override fun init() {
        viewModel.setTestValue("splash success!!")
    }

}