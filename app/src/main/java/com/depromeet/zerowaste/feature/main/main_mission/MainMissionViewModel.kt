package com.depromeet.zerowaste.feature.main.main_mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Ordering
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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

    private val _selectedPlace = MutableLiveData(Place.ALL)
    private val _selectedTheme = MutableLiveData<Theme?>()
    private val _selectedOrder = MutableLiveData(Ordering.RECENT)
    val selectedOrder: LiveData<Ordering> get() = _selectedOrder

    fun initPlaceList() {
        execute({
            val places = ArrayList<Place>()
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

    fun changePlace(place: Place, finish: (() -> Unit)? = null) {
        if(_selectedPlace.value != place) {
            _selectedPlace.value = place
            getMissionList(finish)
        } else {
            finish?.invoke()
        }
    }

    fun changeTheme(theme: Theme?, finish: (() -> Unit)? = null) {
        if(_selectedTheme.value != theme) {
            _selectedTheme.value = theme
            getMissionList(finish)
        } else {
            finish?.invoke()
        }
    }

    fun changeOrder(ordering: Ordering, finish: (() -> Unit)? = null) {
        if(_selectedOrder.value != ordering) {
            _selectedOrder.value = ordering
            getMissionList(finish)
        } else {
            finish?.invoke()
        }
    }

    fun resetOrder() {
        if(Ordering.RECENT != _selectedOrder.value) _selectedOrder.value = Ordering.RECENT
    }

    fun getMissionList(finish: (() -> Unit)? = null) {
        execute({
            val res = MissionApi.getMissions(place = _selectedPlace.value, theme = _selectedTheme.value, ordering = _selectedOrder.value)
            res.data ?: throw Exception(res.message)
        })
        {
            _missionList.value = it
            finish?.invoke()
        }
    }

    fun toggleLikeMission(id:Int, isLiked: Boolean, finish: (Int) -> Unit) {
        execute({
            if (isLiked) MissionApi.dislikeMission(id)
            else MissionApi.likeMission(id)
        }) { finish(it.errorCode) }
    }

}