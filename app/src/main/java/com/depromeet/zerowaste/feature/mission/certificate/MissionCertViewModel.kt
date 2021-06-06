package com.depromeet.zerowaste.feature.mission.certificate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Difficulty

class MissionCertViewModel: BaseViewModel() {

    private val _selectedDifficulty = MutableLiveData<Difficulty>()
    val selectedDifficulty: LiveData<Difficulty> get() = _selectedDifficulty

    private val _editedTxt = MutableLiveData<String>()
    val editedTxt: LiveData<String> get() = _editedTxt

    fun selectDifficulty(difficulty: Difficulty) {
        _selectedDifficulty.value = difficulty
    }

    fun editTxt(txt: String) {
        _editedTxt.value = txt
    }

}