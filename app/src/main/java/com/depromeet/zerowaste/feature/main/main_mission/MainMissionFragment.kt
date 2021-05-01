package com.depromeet.zerowaste.feature.main.main_mission

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMainMissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMissionFragment :
    BaseFragment<FragmentMainMissionBinding>(R.layout.fragment_main_mission) {

    private val viewModel: MainMissionViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
    }

}