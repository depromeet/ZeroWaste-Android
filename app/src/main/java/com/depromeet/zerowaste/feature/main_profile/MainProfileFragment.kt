package com.depromeet.zerowaste.feature.main_profile

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMainProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainProfileFragment :
    BaseFragment<FragmentMainProfileBinding>(R.layout.fragment_main_profile) {

    private val viewModel: MainProfileViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
    }

}