package com.depromeet.zerowaste.feature.main.main_community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.community.Post
import com.depromeet.zerowaste.data.community.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainCommunityViewModel @Inject constructor() : BaseViewModel() {

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> get() = _tagList

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> get() = _postList

    fun initTagList() {
        val tags = ArrayList<Tag>()
        tags.add(Tag("", "", "페트병"))
        tags.add(Tag("", "", "종이팩"))
        tags.add(Tag("", "", "장보기"))
        tags.add(Tag("", "", "쓰레기"))
        tags.add(Tag("", "", "물"))
        _tagList.value = tags
    }

    fun initPostList() {
        val posts = ArrayList<Post>()
        for(i in 0..29) {
            val photos = ArrayList<String>()
            photos.add("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg")
            if(i%3 > 0) photos.add("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg")
            if(i%3 > 1) photos.add("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg")
            posts.add(
                Post(
                    userName = "재활용해요",
                    likeCount = i,
                    isMyLike = i%2 == 0,
                    tag = Tag("","","장보기"),
                    content = "디프만 화이팅 $i",
                    photos = photos,
                    missionName = "환경을 지켜요"
                )
            )
        }
        _postList.value = posts
    }

    fun genNewPostList(): List<Post> {
        val posts = ArrayList<Post>()
        for(i in 0..29) {
            val photos = ArrayList<String>()
            photos.add("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg")
            if(i%3 > 0) photos.add("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg")
            if(i%3 > 1) photos.add("https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg")
            posts.add(
                Post(
                    userName = "재활용해요",
                    likeCount = i,
                    isMyLike = i%2 == 0,
                    tag = Tag("","","장보기"),
                    content = "디프만 화이팅 $i",
                    photos = photos,
                    missionName = "환경을 지켜요"
                )
            )
        }
        return posts
    }

    fun setPostList(posts: List<Post>) {
        _postList.value = posts
    }

}