package com.depromeet.zerowaste.feature.main.main_community

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.App
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.comm.recycleAnimation
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.data.mission.Tag
import com.depromeet.zerowaste.databinding.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainCommunityFragment :
    BaseFragment<FragmentMainCommunityBinding>(R.layout.fragment_main_community) {

    private val viewModel: MainCommunityViewModel by viewModels()

    private val cardAdapter = BaseRecycleAdapter(R.layout.item_main_community_card) { item: Post, bind: ItemMainCommunityCardBinding, _: Int -> bind.item = item }
    @Inject lateinit var listAdapter: MainCommunityListAdapter

    override fun init() {
        binding.vm = viewModel
        binding.fragment = this
        initTagList()
        initPostList()
        binding.mainCommunityTitle.setOnClickListener {
            App.dialog(R.layout.sample_dialog, 400f) { binding: SampleDialogBinding ->

            }
            App.bottomSheet("테스트",
                arrayListOf(
                    Pair(0, "인기순1"),
                    Pair(1, "참여순2"),
                    Pair(2, "참여순3"),
                    Pair(3, "참여순4"),
                    Pair(4, "참여순5"),
                    Pair(5, "참여순6"),
                    Pair(6, "참여순7"),
                    Pair(7, "참여순8"),
                    Pair(8, "참여순9"),
                    Pair(9, "참여순10"),
                    Pair(10, "참여순11"),
                    Pair(11, "참여순12"),
                    Pair(12, "참여순13"),
                    Pair(13, "참여순14"),
                    Pair(14, "참여순15"),
                    Pair(15, "참여순16"),
                    Pair(16, "참여순17"),
                    Pair(17, "참여순18"),
                    Pair(18, "참여순19")
                ),
                0
            ) {
                Log.e("selected", it.toString())
            }
        }
    }

    private fun initTagList() {
        viewModel.initTagList()
        val data = viewModel.tagList.value ?: return
        val adapter = BaseRecycleAdapter(R.layout.item_main_community_tag) { item: Tag, bind: ItemMainCommunityTagBinding, _: Int -> bind.item = item }
        adapter.setData(data)
        binding.mainCommunityTagList.adapter = adapter
    }

    private fun initPostList() {
        viewModel.getPostList.observe(this) { data ->
            cardAdapter.addData(data)
            listAdapter.addData(data)
        }
        val onNeedLoadMore = {
            viewModel.getNewPostList()
        }
        val loadAnimations = recycleAnimation {
            val animator = ObjectAnimator.ofFloat(it, "alpha", 0f, 1f).apply {
                duration = 300L
                interpolator = LinearInterpolator()
            }
            AnimatorSet().apply {
                play(animator)
            }
        }
        cardAdapter.needLoadMore = onNeedLoadMore
        listAdapter.needLoadMore = onNeedLoadMore
        cardAdapter.loadAnimation = loadAnimations
        listAdapter.loadAnimation = loadAnimations

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