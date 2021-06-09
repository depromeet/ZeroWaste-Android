package com.depromeet.zerowaste.feature.suggest

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.MissionApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.Place
import com.depromeet.zerowaste.data.Theme
import com.depromeet.zerowaste.data.mission.CheerSentence
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.data.mission.SuggestMission

class SuggestViewModel: BaseViewModel() {
    private val _checkCanDoNext = MutableLiveData(false)
    val checkCanDoNext: LiveData<Boolean> get() = _checkCanDoNext

    private val _place = MutableLiveData<Place>()
    val place: LiveData<Place> get() = _place

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _contents = MutableLiveData<String>()
    val contents: LiveData<String> get() = _contents

    private val _imgUri = MutableLiveData<Uri>()
    val imgUri: LiveData<Uri> get() = _imgUri

    private val _themeList = MutableLiveData<ArrayList<Theme>>(ArrayList())
    val themeList: LiveData<ArrayList<Theme>> get() = _themeList

    private val _position = MutableLiveData(0)

    private val _createdMissionId = MutableLiveData<Int>()
    val createdMissionId: LiveData<Int> get() = _createdMissionId

    fun checkCanDoNext(position: Int? = null) {
        position?.also { _position.value = it }
        _checkCanDoNext.value = when(_position.value) {
            0 -> place.value?.let { true } ?: false
            1 -> title.value?.let{ true } ?: false
            2 -> contents.value?.let { imgUri.value?.let { true } ?: false } ?: false
            3 -> !themeList.value.isNullOrEmpty()
            4 -> true
            else -> false
        }
    }

    fun setPlace(place: Place) {
        _place.value = place
        checkCanDoNext()
    }

    fun setTitle(title: String) {
        _title.value = title
        checkCanDoNext()
    }

    fun setContents(contents: String) {
        _contents.value = contents
        checkCanDoNext()
    }

    fun setImgUri(uri: Uri) {
        _imgUri.value = uri
        checkCanDoNext()
    }

    fun addTheme(theme: Theme) {
        _themeList.value?.add(theme)
        checkCanDoNext()
    }

    fun removeTheme(theme: Theme) {
        _themeList.value?.remove(theme)
        checkCanDoNext()
    }

    fun startSuggestMission(imgBytes: ByteArray, finish: () -> Unit) {
        execute({
            val suggestMission = SuggestMission(
                _title.value ?: throw Exception("title is null"),
                _place.value ?: throw Exception("place is null"),
                _themeList.value ?: throw Exception("theme is null"),
                Difficulty.EASY,
                _contents.value ?: throw Exception("contents is null"),
                1,
                null
            )
            val created = MissionApi.suggestNewMission(suggestMission).data ?: throw Exception("data is null")
            created.signedUrlList.forEach {
                MissionApi.uploadImage(it, imgBytes)
            }
            _createdMissionId.postValue(created.id)
        }) {
            finish()
        }
    }

    fun updateCheerUp(missionId: Int, contents: String) {
        execute({
            MissionApi.addCheerSentence(missionId, CheerSentence(contents))
        })
    }
}