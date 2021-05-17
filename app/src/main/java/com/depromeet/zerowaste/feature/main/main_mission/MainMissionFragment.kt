package com.depromeet.zerowaste.feature.main.main_mission

import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.SpanStrBuilder
import com.depromeet.zerowaste.data.mission.Rank
import com.depromeet.zerowaste.databinding.FragmentMainMissionBinding
import com.depromeet.zerowaste.databinding.ItemMainMissionRankerBinding
import com.depromeet.zerowaste.feature.main.main_community.MainCommunityListAdapter
import com.depromeet.zerowaste.feature.main.main_community.MainCommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMissionFragment :
    BaseFragment<FragmentMainMissionBinding>(R.layout.fragment_main_mission) {

    private val viewModel: MainMissionViewModel by viewModels()

    private val viewModel2: MainCommunityViewModel by viewModels()

    private val rankerAdapter = BaseRecycleAdapter(R.layout.item_main_mission_ranker) { item: Rank, bind: ItemMainMissionRankerBinding, _ -> bind.item = item }

    override fun init() {
        binding.vm = viewModel

        initTags()
        initRanker()

        binding.mainMissionSuggestTxt.text = SpanStrBuilder(requireContext())
            .add("카페", colorRes = R.color.point)
            .add(requireContext().resources.getString(R.string.main_mission_suggest)).build()

        val adapter = MainCommunityListAdapter()
        viewModel2.getPostList.observe(this) { data ->
            adapter.addData(data)
        }
        binding.mainMissionList.adapter = adapter
        viewModel2.getNewPostList()
    }

    private fun initTags() {
        viewModel.tagList.observe(this) { list ->
            binding.mainMissionTab.removeAllTabs()
            list.forEach { tag ->
                val tab = binding.mainMissionTab.newTab()
                tab.text = tag.title
                binding.mainMissionTab.addTab(tab)
            }
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

}