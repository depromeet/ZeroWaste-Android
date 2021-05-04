package com.depromeet.zerowaste.feature.main.main_community

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.data.community.Tag
import com.depromeet.zerowaste.databinding.FragmentMainCommunityBinding
import com.depromeet.zerowaste.databinding.ItemMainCommunityCardBinding
import com.depromeet.zerowaste.databinding.ItemMainCommunityListBinding
import com.depromeet.zerowaste.databinding.ItemMainCommunityTagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCommunityFragment :
    BaseFragment<FragmentMainCommunityBinding>(R.layout.fragment_main_community) {

    private val viewModel: MainCommunityViewModel by viewModels()

    private val cardAdapter = BaseRecycleAdapter(R.layout.item_main_community_card) { item: Post, vBind: ItemMainCommunityCardBinding, _: Int -> vBind.item = item }
    private val listAdapter = BaseRecycleAdapter(R.layout.item_main_community_list) { item: Post, vBind: ItemMainCommunityListBinding, _: Int -> vBind.item = item }

    override fun init() {
        binding.vm = viewModel
        binding.fragment = this
        initTagList()
        initPostList()
    }

    private fun initTagList() {
        viewModel.initTagList()
        val data = viewModel.tagList.value ?: return
        val adapter = BaseRecycleAdapter(R.layout.item_main_community_tag)
        { item: Tag, vBind: ItemMainCommunityTagBinding, _: Int ->
            vBind.item = item
        }
        adapter.setData(data)
        binding.mainCommunityTagList.adapter = adapter
    }

    private fun initPostList() {
        viewModel.getPostList.observe(this) { data ->
            cardAdapter.addData(data)
            listAdapter.addData(data)
        }
        val onNeelLoadMore = {
            viewModel.getNewPostList()
        }
        cardAdapter.onNeedLoadMore(onNeelLoadMore)
        listAdapter.onNeedLoadMore(onNeelLoadMore)

        binding.mainCommunityCardView.adapter = cardAdapter
        binding.mainCommunityListView.adapter = listAdapter

        viewModel.getNewPostList()
    }

    fun changeListType(isCard: Boolean) {
        if(isCard) {
            binding.mainCommunityCardView.visibility = View.VISIBLE
            binding.mainCommunityListView.visibility = View.GONE
        } else {
            binding.mainCommunityCardView.visibility = View.GONE
            binding.mainCommunityListView.visibility = View.VISIBLE
        }
    }

}