package com.depromeet.zerowaste.feature.main.main_community

import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.databinding.ItemMainCommunityListBinding
import com.depromeet.zerowaste.databinding.ItemMainCommunityPagerImgBinding

class MainCommunityListAdapter: BaseRecycleAdapter<Post, ItemMainCommunityListBinding>(R.layout.item_main_community_list) {

    override fun onDataBind(item: Post, bind: ItemMainCommunityListBinding, position: Int) {
        bind.item = item
        val pagerAdapter = BaseRecycleAdapter(R.layout.item_main_community_pager_img) { pagerItem: String, pagerBind: ItemMainCommunityPagerImgBinding, _: Int -> pagerBind.url = pagerItem }
        pagerAdapter.addData(item.photos)
        bind.mainCommunityListPager.adapter = pagerAdapter
        bind.mainCommunityListIndicator.setViewPager2(bind.mainCommunityListPager)
    }

}