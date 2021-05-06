package com.depromeet.zerowaste.feature.exam

import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseRecycleAdapter
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.databinding.ItemMainCommunityCardBinding

class CustomRecycleAdapter1():
    BaseRecycleAdapter<Post, ItemMainCommunityCardBinding>(R.layout.item_main_community_card,
    { item: Post, bind: ItemMainCommunityCardBinding, _: Int ->
        bind.item = item
    })
{


}

class CustomRecycleAdapter2():
    BaseRecycleAdapter<Post, ItemMainCommunityCardBinding>(R.layout.item_main_community_card, bind()) {

}

private fun bind() = { item: Post, bind: ItemMainCommunityCardBinding, _: Int ->
    bind.item = item
}
