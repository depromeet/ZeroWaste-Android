package com.depromeet.zerowaste.feature.main.main_home

import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.databinding.FragmentMainHomeBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeMissionBinding
import com.depromeet.zerowaste.databinding.ItemMainHomePlaceBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val viewModel: MainHomeViewModel by viewModels()

    private val missionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_mission) { item: Mission, bind: ItemMainHomeMissionBinding, _: Int ->
            bind.mission = item
        }

    private val placeMissionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_place) { item: Place, bind: ItemMainHomePlaceBinding, _: Int ->
            bind.place = item
        }

    override fun init() {
        binding.vm = viewModel
        initView()
        initToolbarLayout()
        initMissionList()
        initMyMissionList()
        initPlaceList()
    }

    private fun initPlaceList() {
        viewModel.placeList.observe(this, { data ->
            placeMissionAdapter.addData(data)
        })
        binding.mainHomeRvPlaceMissions.adapter = placeMissionAdapter
        viewModel.getPlaceList()
    }

    private fun initView() {
        binding.mainHomeTvTitle.text = SpanStrBuilder(requireContext())
            .add("오늘도", colorRes = R.color.black, sp = 22f)
            .add(" 우쥬", colorRes = R.color.sub2, sp = 22f)
            .add("를\n지키러 와주셨군요", colorRes = R.color.black, sp = 22f)
            .build()
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
        // 뷰페이저2 세팅
        val myMissionPagerAdapter = MainHomePagerAdapter(viewModel)
        binding.mainHomeVpMyMissions.adapter = myMissionPagerAdapter
        binding.mainHomeRvMyMissionsIndicator.setViewPager2(binding.mainHomeVpMyMissions)
        // 데이터 로드
        viewModel.myMissionList.observe(this, { data ->
            myMissionPagerAdapter.addItems(data)
        })
        binding.mainHomeRvRecommendMissions.adapter = missionAdapter
        viewModel.getMyMissionList()
    }


}