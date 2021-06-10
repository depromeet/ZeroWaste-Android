package com.depromeet.zerowaste.feature.suggest

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.loadScriptMissionDifficulty
import com.depromeet.zerowaste.comm.loadImageRadius
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase6Binding

class SuggestPhase6Fragment: BaseFragment<FragmentMissionSuggestPhase6Binding>(R.layout.fragment_mission_suggest_phase6) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {}

    override fun onResume() {
        super.onResume()
        binding.missionSuggestPhase6MissionName.text = viewModel.title.value
        viewModel.place.value?.textId?.also { binding.missionSuggestPhase6Place.setText(it) }
        val themeTextSpan = SpanStrBuilder(requireContext())
        viewModel.themeList.value?.forEachIndexed { index, theme ->
            themeTextSpan.add(textId = theme.textId)
            if(index + 1 != viewModel.themeList.value?.size) themeTextSpan.add(",")
        }
        binding.missionSuggestPhase6Type.text = themeTextSpan.build()
        viewModel.imgUri.value?.also {
            loadImageRadius(binding.missionSuggestPhase6HowtoImg, it, 16f)
        }
        loadScriptMissionDifficulty(binding.missionSuggestPhase6Level, viewModel.difficulty.value)
        binding.missionSuggestPhase6HowtoContents.text = viewModel.contents.value
    }
}