package com.depromeet.zerowaste.feature.main.main_mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.mission.Rank
import com.depromeet.zerowaste.data.mission.TempMissionTag
import com.depromeet.zerowaste.data.mission.Tag
import com.depromeet.zerowaste.data.mission.TempMission
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainMissionViewModel @Inject constructor() : BaseViewModel() {

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> get() = _tagList

    private val _rankerList = MutableLiveData<List<Rank>>()
    val rankerList: LiveData<List<Rank>> get() = _rankerList

    private val _missionTagList = MutableLiveData<List<TempMissionTag>>()
    val missionTagList: LiveData<List<TempMissionTag>> get() = _missionTagList

    private val _missionList = MutableLiveData<List<TempMission>>()
    val missionList: LiveData<List<TempMission>> get() = _missionList

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
        rankers.add(Rank(2, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","방효진"))
        rankers.add(Rank(1, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","김종훈"))
        rankers.add(Rank(3, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","김민효"))
        _rankerList.value = rankers
    }

    fun getMission() {
        val tags = ArrayList<TempMissionTag>()
        tags.add(TempMissionTag("재활용"))
        tags.add(TempMissionTag("아껴쓰기"))
        tags.add(TempMissionTag("나눠쓰기"))

        val missions = ArrayList<TempMission>()
        for(i in 0..29) {
            missions.add(
                TempMission(
                "분리수거하기",
                "푸른하늘은하수",
                i,
                Random.nextInt(1000, 9999),
                tags,
                Random.nextBoolean())
            )
        }
        _missionList.value = missions
        _missionTagList.value = tags
    }

}