package com.depromeet.zerowaste.feature.main.main_home

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.genLayoutManager
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.databinding.FragmentMainHomeBinding
import com.depromeet.zerowaste.databinding.ItemMainHomeMissionBinding
import com.depromeet.zerowaste.databinding.ItemMainHomePlaceBinding
import com.depromeet.zerowaste.databinding.ItemMissionTagBinding
import com.depromeet.zerowaste.feature.main.MainFragmentDirections
import com.depromeet.zerowaste.feature.main.MainViewModel
import com.depromeet.zerowaste.feature.mission.detail.MissionDetailViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val viewModel: MainHomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val missionDetailViewModel: MissionDetailViewModel by activityViewModels()

    private val missionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_mission) { item: Mission, bind: ItemMainHomeMissionBinding, position: Int ->
            bind.mission = item
            bind.root.setOnClickListener { missionCardClick(item) }
            bind.mainHomeMissionIvRecommend.setOnClickListener {
                viewModel.toggleLikeMission(item.id, item.isLiked)
                {
                    if (it == 0) {
                        item.isLiked = !item.isLiked
                        this.changeData(item, position)
                    }
                }
            }
            val tagAdapter =
                BaseRecycleAdapter(R.layout.item_mission_tag) { i: Theme, b: ItemMissionTagBinding, _ ->
                    b.item = i
                }
            tagAdapter.setData(item.theme)
            bind.itemMainHomeListTags.layoutManager =
                genLayoutManager(requireContext(), isVertical = false)
            bind.itemMainHomeListTags.adapter = tagAdapter
        }

    private val placeMissionAdapter =
        BaseRecycleAdapter(R.layout.item_main_home_place) { item: Place, bind: ItemMainHomePlaceBinding, _: Int ->
            bind.place = item
        }

    override fun init() {
        binding.vm = viewModel
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

    private fun initTitleView(data: List<Mission>) {
        if (data.isNotEmpty()) { // ??? ????????? ?????? ??????
            binding.mainHomeRvMyMissionsIndicator.visibility = View.VISIBLE
            binding.mainHomeTvTitle.text = SpanStrBuilder(requireContext())
                .add("?????????", colorRes = R.color.black, sp = 22f)
                .add(" ??????", colorRes = R.color.sub2, sp = 22f)
                .add("???\n????????? ???????????????", colorRes = R.color.black, sp = 22f)
                .build()
        } else {
            binding.mainHomeTvTitle.text = SpanStrBuilder(requireContext())
                .add("?????? ?????? ?????????\n????????? ?????????!", colorRes = R.color.black, sp = 22f)
                .build()
            binding.mainHomeRvMyMissionsIndicator.visibility = View.GONE
        }
    }

    private fun initToolbarLayout() {
        binding.run {
            mainHomeLayoutAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { // ????????????
                    mainHomeTvHome.visibility = View.VISIBLE
                } else {// ????????????
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
        // ????????????2 ??????
        val myMissionPagerAdapter = MainHomePagerAdapter(viewModel)
        binding.mainHomeVpMyMissions.adapter = myMissionPagerAdapter
        binding.mainHomeRvMyMissionsIndicator.setViewPager2(binding.mainHomeVpMyMissions)
        // ????????? ??????
        viewModel.myMissionList.observe(this, { data ->
            initTitleView(data)
            myMissionPagerAdapter.addItems(data)
        })
        binding.mainHomeRvRecommendMissions.adapter = missionAdapter
        // viewModel.getMyMissionList()
        viewModel.getMockMyMissionList()
    }

    /*
    * ????????? ??????
    * */
    private fun missionCardClick(mission: Mission) {
        missionDetailViewModel.setMission(mission)
        mainViewModel.navigate(MainFragmentDirections.actionMainFragmentToMissionDetailFragment())
    }


}