package com.depromeet.zerowaste.feature.suggest

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.loadImageRadius
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase5Binding

class SuggestPhase5Fragment: BaseFragment<FragmentMissionSuggestPhase5Binding>(R.layout.fragment_mission_suggest_phase5) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {}

    override fun onResume() {
        super.onResume()
        binding.missionSuggestPhase5MissionName.text = viewModel.title.value
        viewModel.place.value?.textId?.also { binding.missionSuggestPhase5Place.setText(it) }
        val themeTextSpan = SpanStrBuilder(requireContext())
        viewModel.themeList.value?.forEachIndexed { index, theme ->
            themeTextSpan.add(textId = theme.textId)
            if(index + 1 != viewModel.themeList.value?.size) themeTextSpan.add(",")
        }
        binding.missionSuggestPhase5Type.text = themeTextSpan.build()
        viewModel.imgUri.value?.also {
            loadImageRadius(binding.missionSuggestPhase5HowtoImg, it, 16f)
        }
        binding.missionSuggestPhase5HowtoContents.text = viewModel.contents.value
    }
}