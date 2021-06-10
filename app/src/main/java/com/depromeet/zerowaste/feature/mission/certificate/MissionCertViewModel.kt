package com.depromeet.zerowaste.feature.mission.certificate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.CertApi
import com.depromeet.zerowaste.api.RetrofitClient
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.data.cert.CertRequest

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

    fun certificate(missionId: Int, imgByteArrays: List<ByteArray>, finish: () -> Unit) {
        execute({
            val content = _editedTxt.value ?: throw Exception("content is null")
            val difficulty = _selectedDifficulty.value ?: throw Exception("difficulty is null")
            val created = CertApi.certificateMission(missionId, CertRequest(
                "",
                content,
                difficulty,
                imgByteArrays.size
            )).data ?: throw Exception("certificate fail")
            created.signedUrlList.forEachIndexed { i, url ->
                RetrofitClient.uploadImage(url, imgByteArrays[i])
            }
        }) { finish() }
    }

}