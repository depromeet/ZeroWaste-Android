package com.depromeet.zerowaste.feature.mission.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.data.mission.StartParticipateData
import kotlin.Exception

class MissionDetailViewModel: BaseViewModel() {

    private val _missionId = MutableLiveData<Int>()

    private val _mission = MutableLiveData<Mission>()
    val mission: LiveData<Mission> get() = _mission

    private val _participated = MutableLiveData<StartParticipateData>()
    val participated: LiveData<StartParticipateData> get() = _participated

    fun setMission(mission: Mission) {
        _missionId.value = mission.id
        _mission.value = mission
    }

    fun setMission(finish: (Mission) -> Unit) {
        execute({
            val id = _missionId.value ?: throw Exception("fail")
            val res = MissionApi.getMission(id)
            res.data ?: throw Exception(res.message)
        })
        {
            _mission.value = it
            finish(it)
        }
    }

    fun toggleLikeMission(id: Int, isLiked: Boolean, finish: (Int) -> Unit) {
        execute({
            if (isLiked) MissionApi.dislikeMission(id)
            else MissionApi.likeMission(id)
        }) { finish(it.errorCode) }
    }

    fun startParticipate(id: Int, finish: () -> Unit) {
        execute({
            val res = MissionApi.participateMission(id)
            res.data ?: throw Exception(res.message)
        }) {
            _participated.value = it
            finish()
        }
    }
}