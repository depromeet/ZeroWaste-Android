package com.depromeet.zerowaste.feature.mission.approve

import android.os.Build
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.DifTimeStringConverter
import com.depromeet.zerowaste.databinding.FragmentMissionApproveBinding
import com.depromeet.zerowaste.feature.mission.certificate.MissionCertFragment
import com.depromeet.zerowaste.feature.mission.detail.MissionDetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MissionApproveFragment: BaseFragment<FragmentMissionApproveBinding>(R.layout.fragment_mission_approve) {
    override var statusBarBackGroundColorRes = R.color.black
    override var isLightStatusBar = false

    private val viewModel: MissionDetailViewModel by activityViewModels()

    override fun init() {
        binding.item = viewModel.mission.value ?: return
        binding.fragment = this
        initCnt()
    }

    private fun initCnt() {
        val startData = viewModel.participated.value ?: return
        val endTime = startData.endDate.time
        lifecycleScope.launch {
            while (true) {
                binding.missionApproveCnt.text = getString(R.string.mission_detail_limit, DifTimeStringConverter.convert(System.currentTimeMillis(), endTime))
                delay(1000)
            }
        }
    }

    fun clickNow() {
        val missionId = viewModel.mission.value?.id ?: return showToast("missionId is null")
        MissionCertFragment.startMissionCert(this, MissionApproveFragmentDirections.actionMissionApproveFragmentToMissionCertFragment(missionId))
    }

    fun clickNextTime() {
        findNavController().popBackStack()
    }

}