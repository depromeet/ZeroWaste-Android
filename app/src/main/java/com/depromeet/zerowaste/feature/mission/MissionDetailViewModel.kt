package com.depromeet.zerowaste.feature.mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.mission.Mission

class MissionDetailViewModel: BaseViewModel() {

    private val _mission = MutableLiveData<Mission>()
    val mission: LiveData<Mission> get() = _mission

    fun setMission(mission: Mission) {
        _mission.value = mission
    }

    fun toggleLikeMission(id:Int, isLiked: Boolean, finish: (Int) -> Unit) {
        execute({
            if (isLiked) MissionApi.dislikeMission(id)
            else MissionApi.likeMission(id)
        }) { finish(it.errorCode) }
    }
}