package com.depromeet.zerowaste.feature.main.main_home

import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.home.Mission
import com.depromeet.zerowaste.databinding.FragmentMainHomeBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeNewMissionBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val viewModel: MainHomeViewModel by viewModels()

    private val recommendMissionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_new_mission) { item: Mission, bind: ItemMainHomeNewMissionBinding, _: Int ->
            bind.item = item
        }

    override fun init() {
        binding.vm = viewModel
        initToolbarLayout()
        initRecommendMissionList()
        initViewPagerAdapter()
    }

    private fun initViewPagerAdapter() {
        binding.mainHomeVpMyMissions.adapter = MainHomePagerAdapter(viewModel)
        binding.mainHomeRvMyMissionsIndicator.setViewPager2(binding.mainHomeVpMyMissions)
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


    private fun initRecommendMissionList() {
        viewModel.missionList.observe(this, { data ->
            recommendMissionAdapter.addData(data)
        })
        binding.mainHomeRvRecommendMissions.adapter = recommendMissionAdapter
        viewModel.getMissionList()
    }


}