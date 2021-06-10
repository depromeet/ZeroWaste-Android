package com.depromeet.zerowaste.feature.suggest

import android.view.View
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.loadScriptMissionDifficulty
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase4Binding

class SuggestPhase4Fragment: BaseFragment<FragmentMissionSuggestPhase4Binding>(R.layout.fragment_mission_suggest_phase4) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {
        val normal = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)
        val bold = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)
        Difficulty.values().forEachIndexed forIndexed@{ i, difficulty ->
            layoutInflater.inflate(R.layout.layout_suggest_radio, binding.missionSuggestPhase4Radio)
            val radioBtn = binding.missionSuggestPhase4Radio[i] as RadioButton
            val newId = View.generateViewId()
            radioBtn.id = newId
            loadScriptMissionDifficulty(radioBtn, difficulty)
            radioBtn.setOnCheckedChangeListener { v, isChecked ->
                if(isChecked) {
                    viewModel.setDifficulty(difficulty)
                    v.typeface = bold
                }
                else v.typeface = normal
            }
        }
    }

}