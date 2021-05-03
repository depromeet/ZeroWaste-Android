package com.depromeet.zerowaste.feature.main.main_community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.community.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainCommunityViewModel @Inject constructor() : BaseViewModel() {

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> get() = _tagList

    fun initTagList() {
        val tags = ArrayList<Tag>()
        tags.add(Tag("", "페트병"))
        tags.add(Tag("", "종이팩"))
        tags.add(Tag("", "장보기"))
        tags.add(Tag("", "쓰레기"))
        tags.add(Tag("", "물"))
        _tagList.value = tags
    }

}