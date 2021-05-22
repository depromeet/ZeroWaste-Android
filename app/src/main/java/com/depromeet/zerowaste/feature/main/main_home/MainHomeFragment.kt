package com.depromeet.zerowaste.feature.main.main_home

import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.home.Mission
import com.depromeet.zerowaste.databinding.FragmentMainHomeBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeMyMissionBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeNewMissionBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val viewModel: MainHomeViewModel by viewModels()

    private val missionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_my_mission) { item: Mission, bind: ItemMainHomeMyMissionBinding, _: Int ->
            bind.item = item
        }
    private val newMissionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_new_mission) { item: Mission, bind: ItemMainHomeNewMissionBinding, _: Int ->
            bind.item = item
        }

    override fun init() {
        binding.vm = viewModel
//        initToolbarLayout()
//        initMissionList()
//        initNewMissionList()
    }

    private fun initToolbarLayout() {
        binding.run {
            mainHomeLayoutAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { // 접혔을때
                    mainHomeTvHome.visibility = View.VISIBLE
                } else {// 펴졌을때
                    mainHomeTvHome.visibility = View.GONE
                }
            })
        }
    }


    private fun initMissionList() {
        viewModel.missionList.observe(this, { data ->
            missionAdapter.addData(data)
        })
        binding.mainHomeRvRecommendMissions.adapter = missionAdapter
        viewModel.getMissionList()
    }

    private fun initMyMissionList() {
        viewModel.myMissionList.observe(this, { data ->
            newMissionAdapter.addData(data)
        })
        binding.mainHomeRvMyMissions.adapter = newMissionAdapter
        viewModel.getNewMissionList()
    }
}