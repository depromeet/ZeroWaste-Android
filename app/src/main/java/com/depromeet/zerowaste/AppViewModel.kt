package com.depromeet.zerowaste

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.comm.BaseActivity

class AppViewModel(application: MyApplication): AndroidViewModel(application) {

    private val _currentBaseActivity =  MutableLiveData<BaseActivity<*>?>()
    val currentBaseActivity: LiveData<BaseActivity<*>?> get() = _currentBaseActivity

    fun setCurrentBaseActivity(activity: BaseActivity<*>?) {
        _currentBaseActivity.postValue(activity)
    }
}