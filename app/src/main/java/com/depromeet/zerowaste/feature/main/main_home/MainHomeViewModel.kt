package com.depromeet.zerowaste.feature.main.main_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.mission.Mission
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainHomeViewModel @Inject constructor() : BaseViewModel() {
    private val photosUrls = arrayOf(
        "https://live.staticflickr.com/4365/37082384235_af5b13a824_h.jpg",
        "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg",
        "https://live.staticflickr.com/4193/33773666174_b8f26fcbf1_n.jpg"
    )

    private val _missionList = MutableLiveData<List<Mission>>()
    val missionList: LiveData<List<Mission>> get() = _missionList

    private val _myMissionList = MutableLiveData<List<Mission>>()
    val myMissionList: LiveData<List<Mission>> get() = _myMissionList

    private val _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> get() = _placeList

    fun getPlaceList() {
        execute({
            val places = ArrayList<Place>()
            places.add(Place.ALL)
            Place.values().forEach {
                places.add(it)
            }
            places
        }, _placeList, isShowLoad = false)
    }

    fun getMissionWithPlace() {
        execute({
            val res = MissionApi.getMissions()
            res.data ?: throw Exception(res.message)
        })
        {
            _missionList.value = it
        }
    }

    fun getMockMissionList() {
        val missions = ArrayList<Mission>()
/*        for (i in 0..4) {
            missions.add(
                Mission(
                    bannerImgUrls = "",
                    content = "디프만 화이팅 $i",
                    difficulty = i.toString(),
                    id = i,
                    name = "미션제목$i",
                    owner = i,
                    place = "서울$i",
                    sentenceForCheer = "",
                    signedUrlNum = i,
                    theme = i.toString()
                )
            )
        }*/
        _missionList.value = missions
    }
}