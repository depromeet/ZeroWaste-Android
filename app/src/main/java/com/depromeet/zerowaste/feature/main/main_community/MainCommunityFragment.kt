package com.depromeet.zerowaste.feature.main.main_community

import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.genLayoutManager
import com.depromeet.zerowaste.data.community.Tag
import com.depromeet.zerowaste.data.exam.RecycleSampleData
import com.depromeet.zerowaste.databinding.FragmentMainCommunityBinding
import com.depromeet.zerowaste.databinding.FragmentRecyclerSampleItemBinding
import com.depromeet.zerowaste.databinding.ItemMainCommunityTagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCommunityFragment :
    BaseFragment<FragmentMainCommunityBinding>(R.layout.fragment_main_community) {

    private val viewModel: MainCommunityViewModel by viewModels()

    override fun init() {
        binding.vm = viewModel
        setTagList()
    }

    private fun setTagList() {
        viewModel.initTagList()
        val data = viewModel.tagList.value ?: return
        val adapter = BaseRecycleAdapter(R.layout.item_main_community_tag)
        { item: Tag, vBind: ItemMainCommunityTagBinding, _: Int ->
            vBind.item = item
        }
        adapter.setData(data)
        binding.mainCommunityTagList.adapter = adapter
    }

}