package com.depromeet.zerowaste.feature.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.ExamApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.exam.Data1
import com.depromeet.zerowaste.data.exam.Data4
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor() : BaseViewModel() {

    private val _examData = MutableLiveData<Data4>()
    val examData: LiveData<Data4> get() = _examData

    fun runExam(data : Data1) {
        execute({
            ExamApi.post(data)
        }, _examData)
    }

}