package com.depromeet.zerowaste.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseViewModel

class MainViewModel: BaseViewModel() {

    private val _test = MutableLiveData<String>()
    val test : LiveData<String> get() = _test

    fun setTestValue(text: String) {
        _test.value = text
    }

}