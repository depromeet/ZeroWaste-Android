package com.depromeet.zerowaste.feature.suggest

import android.widget.CheckBox
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase4Binding


class SuggestPhase4Fragment: BaseFragment<FragmentMissionSuggestPhase4Binding>(R.layout.fragment_mission_suggest_phase4) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {
        var checkedCount = 0
        Theme.values().forEachIndexed { i, theme ->
            layoutInflater.inflate(R.layout.layout_suggest_checkbox, binding.missionSuggestCheckboxList)
            val checkBox = binding.missionSuggestCheckboxList.getChildAt(i) as CheckBox
            checkBox.setText(theme.textId)
            checkBox.setOnCheckedChangeListener { v, isChecked ->
                if(isChecked) {
                    checkedCount++
                    if(checkedCount > 3) {
                        v.isChecked = false
                        checkedCount--
                    } else {
                        viewModel.addTheme(theme)
                    }
                } else {
                    checkedCount--
                    viewModel.removeTheme(theme)
                }
            }
        }
    }
}