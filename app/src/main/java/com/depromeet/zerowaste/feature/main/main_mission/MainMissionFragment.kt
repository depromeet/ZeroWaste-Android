package com.depromeet.zerowaste.feature.main.main_mission

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.databinding.FragmentMainMissionBinding
import com.depromeet.zerowaste.feature.main.main_community.MainCommunityListAdapter
import com.depromeet.zerowaste.feature.main.main_community.MainCommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMissionFragment :
    BaseFragment<FragmentMainMissionBinding>(R.layout.fragment_main_mission) {

    private val viewModel: MainMissionViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel

        binding.mainMissionTxt.text = SpanStrBuilder(requireContext())
            .add("카페", colorRes = R.color.point, sp = 20f)
            .add(requireContext().resources.getString(R.string.main_mission_suggest)).build()

   }

}