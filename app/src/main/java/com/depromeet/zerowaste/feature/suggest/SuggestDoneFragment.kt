package com.depromeet.zerowaste.feature.suggest

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestDoneBinding

class SuggestDoneFragment: BaseFragment<FragmentMissionSuggestDoneBinding>(R.layout.fragment_mission_suggest_done) {

    private val viewModel: SuggestViewModel by viewModels()
    private val args: SuggestDoneFragmentArgs by navArgs()

    override fun init() {
        binding.missionSuggestCheerTxt.text = SpanStrBuilder(requireContext())
            .add(textId = R.string.mission_suggest_cheer, colorRes = R.color.gray_1)
            .add("(${resources.getString(R.string.choice)})", colorRes = R.color.purple)
            .build()
        binding.missionSuggestDoneHome.setOnClickListener {
            val cheerSentence = binding.missionSuggestCheerEdit.text.toString()
            if(cheerSentence.isNotEmpty()) {
                viewModel.updateCheerUp(args.missionId, cheerSentence) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}