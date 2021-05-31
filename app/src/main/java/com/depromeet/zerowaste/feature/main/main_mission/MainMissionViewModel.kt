package com.depromeet.zerowaste.feature.main.main_mission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainMissionViewModel @Inject constructor() : BaseViewModel() {

    private val _placeList = MutableLiveData<List<Place>>()
    val placeList: LiveData<List<Place>> get() = _placeList

    private val _rankerList = MutableLiveData<List<Rank>>()
    val rankerList: LiveData<List<Rank>> get() = _rankerList

    private val _missionTagList = MutableLiveData<List<MissionTag>>()
    val missionTagList: LiveData<List<MissionTag>> get() = _missionTagList

    private val _missionList = MutableLiveData<List<Mission>>()
    val missionList: LiveData<List<Mission>> get() = _missionList

    fun initPlaceList() {
        execute({
            val places = ArrayList<Place>()
            places.add(Place.ALL)
            Place.values().forEach {
                places.add(it)
            }
            places
        }, _placeList, isShowLoad = false)
    }

    fun initTagList() {
        execute({
            val tags = ArrayList<MissionTag>()
            Theme.values().forEach {
                tags.add(MissionTag(it))
            }
            tags
        }, _missionTagList, isShowLoad = false)
    }

    fun refreshRankerList() {
        execute({
            val rankers = ArrayList<Rank>()
            rankers.add(Rank(2, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","방효진"))
            rankers.add(Rank(1, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","김종훈"))
            rankers.add(Rank(3, "https://live.staticflickr.com/7841/33492970008_d7c8040ca7_n.jpg","김민효"))
            rankers
        }, _rankerList, isShowLoad = false)
    }

    fun getMissionWithPlace(place: Place, finish: () -> Unit) {
        execute({
            val res = MissionApi.getMissions(place = place)
            refreshRankerList()
            res.data ?: throw Exception(res.message)
        })
        {
            _missionList.value = it
            finish()
        }
    }

    fun refreshMissionWithTag(place: Place, theme: Theme?, finish: () -> Unit) {
        execute({
            val res = MissionApi.getMissions(
                place = place,
                theme = theme
            )
            res.data ?: throw Exception(res.message)
        })
        {
            _missionList.value = it
            finish()
        }
    }

}