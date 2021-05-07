package com.depromeet.zerowaste.feature.main.main_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.home.Mission
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

    private val _newMissionList = MutableLiveData<List<Mission>>()
    val newMissionList: LiveData<List<Mission>> get() = _newMissionList


    fun getMissionList() {
        val missions = ArrayList<Mission>()
        for (i in 0..29) {
            missions.add(
                Mission(
                    iconImg = photosUrls[0],
                    logo = photosUrls[1],
                    content = "디프만 화이팅 $i"
                )
            )
        }
        _missionList.value = missions
    }

    fun getNewMissionList() {
        val missions = ArrayList<Mission>()
        for (i in 0..29) {
            missions.add(
                Mission(
                    iconImg = photosUrls[2],
                    logo = photosUrls[1],
                    content = "디프만 화이팅 $i"
                )
            )
        }
        _newMissionList.value = missions
    }
}