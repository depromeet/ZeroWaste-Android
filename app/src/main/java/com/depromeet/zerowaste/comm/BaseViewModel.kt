package com.depromeet.zerowaste.comm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel() {

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> get() = _error

    protected fun <T> execute(job: suspend CoroutineScope.() -> T, context: CoroutineContext = Dispatchers.IO, res: (T) -> Unit)  {
        viewModelScope.launch(context) {
            try {
                val result = job()
                launch(Dispatchers.Main) {
                    res(result)
                }
            } catch (e: Exception){
                e.printStackTrace()
                _error.postValue(e)
            }
        }
    }

    protected fun <T> execute(job: suspend CoroutineScope.() -> T, resData: MutableLiveData<T>, context: CoroutineContext = Dispatchers.IO)  {
        viewModelScope.launch(context) {
            try {
                val result = job()
                resData.postValue(result)
            } catch (e: Exception){
                e.printStackTrace()
                _error.postValue(e)
            }
        }
    }
}