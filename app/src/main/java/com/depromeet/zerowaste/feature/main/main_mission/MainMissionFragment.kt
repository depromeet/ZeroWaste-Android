package com.depromeet.zerowaste.feature.main.main_mission

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.data.mission.Rank
import com.depromeet.zerowaste.data.mission.TempMission
import com.depromeet.zerowaste.data.mission.TempMissionTag
import com.depromeet.zerowaste.databinding.*
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainMissionFragment :
    BaseFragment<FragmentMainMissionBinding>(R.layout.fragment_main_mission) {

    private val viewModel: MainMissionViewModel by viewModels()

    private val rankerAdapter = BaseRecycleAdapter(R.layout.item_main_mission_ranker) { item: Rank, bind: ItemMainMissionRankerBinding, _ -> bind.item = item }
    private val chipAdapter = BaseRecycleAdapter(R.layout.item_main_mission_chip)
    { item: TempMissionTag, bind: ItemMainMissionChipBinding, position ->
        bind.itemMainMissionChip.setOnClickListener {
            missionChipClick(position)
        }
        bind.item = item
    }
    private val missionAdapter = BaseRecycleAdapter(R.layout.item_main_mission_list)
    { item: TempMission, bind: ItemMainMissionListBinding, _ ->
        bind.item = item
        val tagAdapter = BaseRecycleAdapter(R.layout.item_main_mission_list_tag){ i: TempMissionTag, b: ItemMainMissionListTagBinding, _ -> b.item = i }
        tagAdapter.setData(item.tagList)
        bind.itemMainMissionListTags.adapter = tagAdapter
    }

    override fun init() {
        binding.vm = viewModel
        initTags()
        initRanker()
        initMissions()
    }

    private fun initTags() {
        viewModel.tagList.observe(this) { list ->
            binding.mainMissionTab.removeAllTabs()
            binding.mainMissionTab.clearOnTabSelectedListeners()
            list.forEach { tag ->
                val tab = binding.mainMissionTab.newTab()
                tab.text = tag.title
                binding.mainMissionTab.addTab(tab)
            }
            binding.mainMissionTab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) { tabSelected(tab.position) }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
        viewModel.initTagList()
    }

    private fun initRanker() {
        viewModel.rankerList.observe(this) {
            rankerAdapter.setData(it)
        }
        binding.mainMissionRanker.adapter = rankerAdapter
        viewModel.refreshRankerList()
    }

    private fun initMissions() {
        viewModel.missionTagList.observe(this) {
            chipAdapter.setData(it)
        }
        binding.mainMissionChips.adapter = chipAdapter
        viewModel.missionList.observe(this) {
            missionAdapter.setData(it)
        }
        binding.mainMissionList.adapter = missionAdapter
        viewModel.getMission()
    }

    private fun missionChipClick(position: Int) {
        chipAdapter.getItems().forEachIndexed { i, item ->
            item.selected = if(position == i) !item.selected else false
        }
        chipAdapter.notifyDataSetChanged()
        binding.mainMissionMotion.transitionToEnd()
    }

    private fun tabSelected(position: Int) {
        viewModel.tagList.value?.get(position)?.title?.also {
            binding.mainMissionSuggestTxt.text = SpanStrBuilder(requireContext())
                .add(it, colorRes = R.color.sub2)
                .add(requireContext().resources.getString(R.string.main_mission_suggest))
                .build()
        }
        viewModel.getMission()
        binding.mainMissionMotion.transitionToStart()
        binding.mainMissionListNested.smoothScrollTo(0,0)
    }

}