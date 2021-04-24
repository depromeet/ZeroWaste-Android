package com.depromeet.zerowaste.feature.main_home

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMainHomeBinding
import com.depromeet.zerowaste.feature.main_community.MainCommunityViewModel


class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val viewModel: MainHomeViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
    }
}