package com.depromeet.zerowaste.feature.main.main_community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.data.community.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainCommunityViewModel @Inject constructor() : BaseViewModel() {

    private val photosUrls = arrayOf("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg", "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg", "https://live.staticflickr.com/4193/33773666174_b8f26fcbf1_n.jpg")

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> get() = _tagList

    private val _getPostList = MutableLiveData<List<Post>>()
    val getPostList: LiveData<List<Post>> get() = _getPostList

    fun initTagList() {
        val tags = ArrayList<Tag>()
        tags.add(Tag("", "", "페트병"))
        tags.add(Tag("", "", "종이팩"))
        tags.add(Tag("", "", "장보기"))
        tags.add(Tag("", "", "쓰레기"))
        tags.add(Tag("", "", "물"))
        _tagList.value = tags
    }

    fun getNewPostList() {
        val posts = ArrayList<Post>()
        for(i in 0..29) {
            val photos = ArrayList<String>()
            photos.add(photosUrls[Random.nextInt(0,3)])
            if(i%3 > 0) photos.add(photosUrls[Random.nextInt(0,3)])
            if(i%3 > 1) photos.add(photosUrls[Random.nextInt(0,3)])
            posts.add(
                Post(
                    userName = "재활용해요",
                    userBadge = "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg",
                    userMissionCount = 10,
                    likeCount = i,
                    isMyLike = i%2 == 0,
                    tag = Tag("","","장보기"),
                    content = "디프만 화이팅 $i",
                    photos = photos,
                    missionName = "환경을 지켜요"
                )
            )
        }
        _getPostList.value = posts
    }

}