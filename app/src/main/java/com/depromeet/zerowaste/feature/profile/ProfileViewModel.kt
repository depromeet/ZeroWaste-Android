package com.depromeet.zerowaste.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.ParticipateStatus
import com.depromeet.zerowaste.data.mission.Mission

class ProfileViewModel : BaseViewModel() {
    private val _participatedMissionList = MutableLiveData<List<Mission>>()
    val participatedMissionList: LiveData<List<Mission>> get() = _participatedMissionList

    private val _likedMissionList = MutableLiveData<List<Mission>>()
    val likedMissionList: LiveData<List<Mission>> get() = _likedMissionList

    fun getParticipatedMissionList(finish: (() -> Unit)? = null) { //인증한 미션
        execute({
            val res = MissionApi.getMissions()
            res.data ?: throw Exception(res.message)
        })
        {
            _participatedMissionList.value = it.filter { mission ->
                mission.participation.status.value == ParticipateStatus.SUCCESS.value
            }
            finish?.invoke()
        }
    }

    fun getLikedMissionList(finish: (() -> Unit)? = null) { // 좋아요한 미션
        execute({
            val res = MissionApi.getMissions()
            res.data ?: throw Exception(res.message)
        })
        {
            _likedMissionList.value = it.filter { mission -> mission.isLiked }
            finish?.invoke()
        }
    }
}