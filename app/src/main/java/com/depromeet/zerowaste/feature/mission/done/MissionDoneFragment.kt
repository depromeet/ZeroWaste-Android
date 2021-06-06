package com.depromeet.zerowaste.feature.mission.done

import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMissionDoneBinding

class MissionDoneFragment: BaseFragment<FragmentMissionDoneBinding>(R.layout.fragment_mission_done) {
    override var statusBarBackGroundColorRes = R.color.black
    override var isLightStatusBar = false

    override fun init() {
        binding.missionDoneHome.setOnClickListener {
            findNavController().popBackStack(R.id.mainFragment, true)
        }
    }
}