package com.depromeet.zerowaste.feature.main.main_community

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.data.community.Tag
import com.depromeet.zerowaste.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCommunityFragment :
    BaseFragment<FragmentMainCommunityBinding>(R.layout.fragment_main_community) {

    private val viewModel: MainCommunityViewModel by activityViewModels()

    private val cardAdapter = BaseRecycleAdapter(R.layout.item_main_community_card) { item: Post, bind: ItemMainCommunityCardBinding, _: Int -> bind.item = item }
    private val listAdapter = MainCommunityListAdapter()

    override fun init() {
        binding.vm = viewModel
        binding.fragment = this
        initTagList()
    }

    private fun initTagList() {
        viewModel.initTagList()
        val data = viewModel.tagList.value ?: return
        val adapter = BaseRecycleAdapter(R.layout.item_main_community_tag) { item: Tag, bind: ItemMainCommunityTagBinding, _: Int -> bind.item = item }
        adapter.setData(data)
        binding.mainCommunityTagList.adapter = adapter
    }

    private fun onResumePostList() {
        viewModel.getPostList.observe(this) { data ->
            cardAdapter.addData(data)
            listAdapter.addData(data)
        }
        val onNeedLoadMore = {
            viewModel.getNewPostList()
        }
        cardAdapter.needLoadMore = onNeedLoadMore
        listAdapter.needLoadMore = onNeedLoadMore

        binding.mainCommunityCardView.adapter = cardAdapter
        binding.mainCommunityListView.adapter = listAdapter

        viewModel.isSelectCard.value?.also {
            changeListType(it)
        }

        if(viewModel.postList.value == null || viewModel.postList.value?.size == 0) {
            viewModel.getNewPostList()
        } else {
            cardAdapter.setData(viewModel.postList.value)
            listAdapter.setData(viewModel.postList.value)
        }

        viewModel.cardState.value?.also {
            binding.mainCommunityCardView.layoutManager?.onRestoreInstanceState(it)
        }

        viewModel.listState.value?.also {
            binding.mainCommunityListView.layoutManager?.onRestoreInstanceState(it)
        }
    }


    private fun onPausePostList() {
        binding.mainCommunityCardView.layoutManager?.onSaveInstanceState()?.also {
            viewModel.setCardState(it)
        }
        binding.mainCommunityListView.layoutManager?.onSaveInstanceState()?.also {
            viewModel.setListState(it)
        }
        viewModel.getPostList.removeObservers(this)
    }


    override fun onResume() {
        super.onResume()
        onResumePostList()
    }

    override fun onPause() {
        super.onPause()
        onPausePostList()
    }

    fun changeListType(isCard: Boolean) {
        if(isCard) {
            binding.mainCommunityCardView.visibility = View.VISIBLE
            binding.mainCommunityListView.visibility = View.GONE
            viewModel.isSelectCard(true)
        } else {
            binding.mainCommunityCardView.visibility = View.GONE
            binding.mainCommunityListView.visibility = View.VISIBLE
            viewModel.isSelectCard(false)
        }
    }

}