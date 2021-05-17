package com.depromeet.zerowaste.feature.main.main_mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.mission.Rank
import com.depromeet.zerowaste.data.mission.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMissionViewModel @Inject constructor() : BaseViewModel() {

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> get() = _tagList

    private val _rankerList = MutableLiveData<List<Rank>>()
    val rankerList: LiveData<List<Rank>> get() = _rankerList

    fun initTagList() {
        val tags = ArrayList<Tag>()
        tags.add(Tag("", "", "전체"))
        tags.add(Tag("", "", "주방"))
        tags.add(Tag("", "", "카페"))
        tags.add(Tag("", "", "식당"))
        tags.add(Tag("", "", "기타"))
        _tagList.value = tags
    }

    fun refreshRankerList() {
        val rankers = ArrayList<Rank>()
        rankers.add(Rank(1, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","방효진"))
        rankers.add(Rank(2, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","김종훈"))
        rankers.add(Rank(3, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","김민효"))
        _rankerList.value = rankers
    }

}