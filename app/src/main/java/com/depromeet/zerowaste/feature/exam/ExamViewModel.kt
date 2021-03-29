package com.depromeet.zerowaste.feature.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.exam.ExamApi
import com.depromeet.zerowaste.data.exam.Data4
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.data.exam.Data1

class ExamViewModel: BaseViewModel() {

    private val _examData = MutableLiveData<Data4>()
    val examData: LiveData<Data4> get() = _examData

    fun runExam(data : Data1) {
        execute({
            ExamApi.post(data)
        }, _examData)
    }

}