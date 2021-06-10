package com.depromeet.zerowaste.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.depromeet.zerowaste.comm.BaseViewModel

class MainViewModel: BaseViewModel() {

    private val _navDirection = MutableLiveData<NavDirections>()
    val navDirection: LiveData<NavDirections> get() = _navDirection

    private val _certMissionId = MutableLiveData<Int>()
    val certMissionId: LiveData<Int> get() = _certMissionId

    fun navigate(direction: NavDirections) {
        _navDirection.postValue(direction)
    }

    fun startCertificate(missionId: Int) {
        _certMissionId.postValue(missionId)
    }
}