package com.depromeet.zerowaste.feature.main.main_community

import android.util.Log
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainCommunityViewModel @Inject constructor() : BaseViewModel() {


    fun getMissionTest() {
        execute({
            MissionApi.getMissions(theme = Theme.ROT)
        }) {
            Log.e("tttt", it.toString())
        }
    }
}