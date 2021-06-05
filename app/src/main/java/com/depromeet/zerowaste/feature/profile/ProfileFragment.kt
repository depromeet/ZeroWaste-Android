package com.depromeet.zerowaste.feature.profile

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.databinding.FragmentProfileBinding
import com.depromeet.zerowaste.databinding.ItemProfileMissionBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    private val missionAdapter =
        BaseRecycleAdapter(R.layout.item_profile_mission) { item: Mission, bind: ItemProfileMissionBinding, position: Int ->
            bind.item = item
            bind.root.setOnClickListener {

            }
        }

    override fun init() {
        binding.vm = viewModel
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        initTabLayout()
        initMissionList()
    }

    private fun initMissionList() {
        viewModel.participatedMissionList.observe(this, { data ->
            missionAdapter.addData(data)
        })
        viewModel.likedMissionList.observe(this, { data ->
            missionAdapter.addData(data)
        })
        binding.mainProfileRvMissions.adapter = missionAdapter
        viewModel.getParticipatedMissionList()
    }

    private fun initTabLayout() {
        binding.mainProfileLayoutTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 0) {
                    viewModel.getParticipatedMissionList()
                } else if (position == 1) {
                    viewModel.getLikedMissionList()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}