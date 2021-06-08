package com.depromeet.zerowaste.feature.suggest

import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestDoneBinding

class SuggestDoneFragment: BaseFragment<FragmentMissionSuggestDoneBinding>(R.layout.fragment_mission_suggest_done) {

    override fun init() {
        binding.missionSuggestCheerTxt.text = SpanStrBuilder(requireContext())
            .add(textId = R.string.mission_suggest_cheer, colorRes = R.color.gray_1)
            .add("(${resources.getString(R.string.choice)})", colorRes = R.color.purple)
            .build()
    }
}