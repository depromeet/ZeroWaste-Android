package com.depromeet.zerowaste.comm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> get() = _error

    protected fun <T> execute(job: suspend CoroutineScope.() -> T, dispatcher: CoroutineDispatcher = Dispatchers.IO, res: (T) -> Unit)  {
        viewModelScope.launch(dispatcher) {
            try {
                val result = job()
                launch(Dispatchers.Main) {
                    res.invoke(result)
                }
            } catch (e: java.lang.Exception){
                e.printStackTrace()
                _error.postValue(e)
            }
        }
    }

    protected fun <T> execute(job: suspend CoroutineScope.() -> T, resData: MutableLiveData<T>, dispatcher: CoroutineDispatcher = Dispatchers.IO)  {
        viewModelScope.launch(dispatcher) {
            try {
                val result = job()
                resData.postValue(result)
            } catch (e: java.lang.Exception){
                e.printStackTrace()
                _error.postValue(e)
            }
        }
    }
}