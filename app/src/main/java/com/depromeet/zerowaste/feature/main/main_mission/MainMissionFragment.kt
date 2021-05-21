package com.depromeet.zerowaste.feature.main.main_mission

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.comm.genLayoutManager
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
        val tagAdapter = BaseRecycleAdapter(R.layout.item_main_mission_list_tag){ i: TempMissionTag, b: ItemMainMissionListTagBinding, _ -> b.item = i }
        tagAdapter.setData(item.tagList)
        bind.itemMainMissionListTags.layoutManager = genLayoutManager(requireContext(), isVertical = false)
        bind.itemMainMissionListTags.adapter = tagAdapter
        bind.item = item
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
            binding.mainMissionChips.post {
                chipAdapter.setData(it)
            }
        }
        binding.mainMissionChips.adapter = chipAdapter
        viewModel.missionList.observe(this) {
            binding.mainMissionList.post {
                missionAdapter.setData(it)
            }
        }
        binding.mainMissionList.adapter = missionAdapter
        viewModel.getMission()
    }

    private fun missionChipClick(position: Int) {
        if(binding.mainMissionMotion.progress != 1f) binding.mainMissionMotion.transitionToEnd()
        chipAdapter.getItems().forEachIndexed { i, item ->
            item.selected = if(position == i) !item.selected else false
        }
        chipAdapter.notifyDataSetChanged()
    }

    private fun tabSelected(position: Int) {
        viewModel.tagList.value?.get(position)?.title?.also {
            binding.mainMissionSuggestTxt.text = SpanStrBuilder(requireContext())
                .add(it, colorRes = R.color.sub2)
                .add(requireContext().resources.getString(R.string.main_mission_suggest))
                .build()
        }
        viewModel.getMission()
        if(binding.mainMissionMotion.progress != 0f) binding.mainMissionMotion.transitionToStart()
        binding.mainMissionListNested.smoothScrollTo(0,0)
    }

}