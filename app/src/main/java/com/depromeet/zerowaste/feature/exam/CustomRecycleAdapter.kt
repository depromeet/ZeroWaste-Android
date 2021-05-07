package com.depromeet.zerowaste.feature.exam

import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.databinding.ItemMainCommunityCardBinding

class CustomRecycleAdapter: BaseRecycleAdapter<Post, ItemMainCommunityCardBinding>(R.layout.item_main_community_card) {
    override fun onDataBind(item: Post, bind: ItemMainCommunityCardBinding, position: Int) {
        bind.item = item
    }
}
