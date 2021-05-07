package com.depromeet.zerowaste.feature.main.main_home

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.home.Mission
import com.depromeet.zerowaste.databinding.FragmentMainHomeBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeMissionBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeNewMissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val viewModel: MainHomeViewModel by viewModels()

    private val missionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_mission) { item: Mission, bind: ItemMainHomeMissionBinding, _: Int ->
            bind.item = item
        }
    private val newMissionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_new_mission) { item: Mission, bind: ItemMainHomeNewMissionBinding, _: Int ->
            bind.item = item
        }

    override fun init() {
        binding.vm = viewModel
        initMissionList()
        initNewMissionList()
    }

    private fun initMissionList() {
        viewModel.missionList.observe(this) { data ->
            missionAdapter.addData(data)
        }
        binding.homeRvMissions.adapter = missionAdapter
        viewModel.getMissionList()
    }

    private fun initNewMissionList() {
        viewModel.newMissionList.observe(this) { data ->
            newMissionAdapter.addData(data)
        }
        binding.homeRvNewMissions.adapter = newMissionAdapter
        viewModel.getNewMissionList()
    }
}