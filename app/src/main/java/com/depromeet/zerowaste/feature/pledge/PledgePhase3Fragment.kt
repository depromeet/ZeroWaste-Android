package com.depromeet.zerowaste.feature.pledge

import android.text.InputFilter
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentPledgePhase3Binding
import java.util.regex.Pattern

class PledgePhase3Fragment :
    BaseFragment<FragmentPledgePhase3Binding>(R.layout.fragment_pledge_phase3) {
    override var statusBarBackGroundColorRes = R.color.white
    override var isLightStatusBar = true

    private val viewModel: PledgeViewModel by viewModels({requireParentFragment()})

    override fun init() {
        binding.pledgePhase3EtPledge.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            val ps = Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$")
            if (source.equals("") || ps.matcher(source).matches()) source else ""
        }, InputFilter.LengthFilter(10))
        binding.pledgePhase3EtPledge.addTextChangedListener {
            it?.toString()?.also { nickName ->
                viewModel.setNickname(nickName)
            }
        }
        binding.pledgePhase3EtPledge.setOnEditorActionListener { _, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE) {
                viewModel.setActionDone(true)
                true
            }
            else false
        }
    }
}