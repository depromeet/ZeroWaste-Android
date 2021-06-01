package com.depromeet.zerowaste.feature.main.main_mission

import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.genLayoutManager
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.data.mission.MissionTag
import com.depromeet.zerowaste.data.mission.Rank
import com.depromeet.zerowaste.databinding.*
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainMissionFragment :
    BaseFragment<FragmentMainMissionBinding>(R.layout.fragment_main_mission) {
    override var statusBarBackGroundColorRes = R.color.black
    override var isLightStatusBar = false

    private val viewModel: MainMissionViewModel by viewModels()

    private val rankerAdapter = BaseRecycleAdapter(R.layout.item_main_mission_ranker) { item: Rank, bind: ItemMainMissionRankerBinding, _ -> bind.item = item }
    private val chipAdapter = BaseRecycleAdapter(R.layout.item_main_mission_chip)
    { item: MissionTag, bind: ItemMainMissionChipBinding, position ->
        bind.itemMainMissionChip.setOnClickListener {
            missionChipClick(position)
        }
        bind.item = item
    }
    private val missionAdapter = BaseRecycleAdapter(R.layout.item_main_mission_list)
    { item: Mission, bind: ItemMainMissionListBinding, _ ->
        val tagAdapter = BaseRecycleAdapter(R.layout.item_main_mission_list_tag){ i: Theme, b: ItemMainMissionListTagBinding, _ -> b.item = i }
        tagAdapter.setData(item.theme)
        bind.itemMainMissionListTags.layoutManager = genLayoutManager(requireContext(), isVertical = false)
        bind.itemMainMissionListTags.adapter = tagAdapter
        bind.item = item
    }

    private var selectedPlace: Place = Place.ALL

    override fun init() {
        binding.vm = viewModel
        viewModel.error.observe(this) {
            showToast(it.message.toString())
        }
        /*
        * 데이터 옵저버 동작, 뷰 이벤트 세팅
        * */
        initMissions()
        initRanker()
        initMissionTags()
        initPlaces()
        /*
        * 데이터 값 초기 세팅
        * */
        initData()
    }

    private fun initMissions() {
        binding.mainMissionList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0 && binding.mainMissionMotion.progress != 1f) {
                    binding.mainMissionMotion.progress += Float.MIN_VALUE
                }
            }
        })
        viewModel.missionList.observe(this) {
            binding.mainMissionList.post {
                missionAdapter.setData(it)
            }
        }
        binding.mainMissionList.adapter = missionAdapter
    }

    private fun initRanker() {
        viewModel.rankerList.observe(this) {
            rankerAdapter.setData(it)
        }
        binding.mainMissionRanker.adapter = rankerAdapter
    }

    private fun initMissionTags() {
        viewModel.missionTagList.observe(this) {
            binding.mainMissionChips.post {
                chipAdapter.setData(it)
            }
        }
        binding.mainMissionChips.adapter = chipAdapter
        viewModel.initTagList()
    }

    private fun initPlaces() {
        viewModel.placeList.observe(this) { list ->
            binding.mainMissionTab.removeAllTabs()
            binding.mainMissionTab.clearOnTabSelectedListeners()
            list.forEach { place ->
                val tab = binding.mainMissionTab.newTab()
                tab.text = resources.getString(place.textId)
                binding.mainMissionTab.addTab(tab)
            }
            binding.mainMissionTab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) { tabSelected(tab.position) }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
        viewModel.initPlaceList()
    }

    private fun initData() {
        tabSelected(0)
    }

    private fun missionChipClick(position: Int) {
        val refreshFinish = {
            chipAdapter.notifyDataSetChanged()
            if(binding.mainMissionMotion.progress != 1f) binding.mainMissionMotion.transitionToEnd()
        }
        chipAdapter.getItems().forEachIndexed { i, item ->
            item.selected = if(position == i) {
                viewModel.refreshMissionWithTag(selectedPlace, if(item.selected) null else item.theme, refreshFinish)
                !item.selected
            } else false
        }
    }

    private fun tabSelected(position: Int) {
        val place = viewModel.placeList.value?.get(position) ?: selectedPlace
        viewModel.getMissionWithPlace(place)
        {
            selectedPlace = place
            binding.mainMissionSuggestTxt.text = SpanStrBuilder(requireContext())
                .add(textId = place.textId)
                .add(textId = R.string.main_mission_suggest)
                .build()
            chipAdapter.getItems().forEach { item -> item.selected = false }
            chipAdapter.notifyDataSetChanged()
            if(binding.mainMissionMotion.progress != 0f) binding.mainMissionMotion.transitionToStart()
            binding.mainMissionList.scrollTo(0, 0)
        }
    }
}