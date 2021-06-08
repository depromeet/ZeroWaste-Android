package com.depromeet.zerowaste.feature.suggest

import android.view.View
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase1Binding

class SuggestPhase1Fragment: BaseFragment<FragmentMissionSuggestPhase1Binding>(R.layout.fragment_mission_suggest_phase1) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {
        val normal = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)
        val bold = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)
        Place.values().forEachIndexed forIndexed@{ i, place ->
            layoutInflater.inflate(R.layout.layout_suggest_radio, binding.missionSuggestPhase1Radio)
            val radioBtn = binding.missionSuggestPhase1Radio[i] as RadioButton
            val newId = View.generateViewId()
            radioBtn.id = newId
            radioBtn.setText(place.textId)
            radioBtn.setOnCheckedChangeListener { v, isChecked ->
                if(isChecked) {
                    viewModel.setPlace(place)
                    v.typeface = bold
                }
                else v.typeface = normal
            }
            if(place == Place.ALL) radioBtn.visibility = View.GONE
        }
    }

}