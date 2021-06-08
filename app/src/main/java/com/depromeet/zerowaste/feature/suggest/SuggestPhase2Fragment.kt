package com.depromeet.zerowaste.feature.suggest

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase2Binding

class SuggestPhase2Fragment: BaseFragment<FragmentMissionSuggestPhase2Binding>(R.layout.fragment_mission_suggest_phase2) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {
        binding.missionSuggestPhase2Edit.addTextChangedListener {
            it?.toString()?.also { title ->
                viewModel.setTitle(title)
            }
        }
    }
}