package com.depromeet.zerowaste.comm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.zerowaste.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel() {

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> get() = _error

    protected fun <T> execute(job: suspend CoroutineScope.() -> T, context: CoroutineContext = Dispatchers.IO, isShowLoad: Boolean = true, res: (T) -> Unit) {
        viewModelScope.launch(context) {
            launch(Dispatchers.Main) { if(isShowLoad) App.startLoad() }
            try {
                val result = job()
                launch(Dispatchers.Main) {
                    res(result)
                    if(isShowLoad) App.finishLoad()
                }
            } catch (e: Exception){
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    _error.value = e
                    if(isShowLoad) App.finishLoad()
                }
            }
        }
    }

    protected fun <T> execute(job: suspend CoroutineScope.() -> T, resData: MutableLiveData<T>, context: CoroutineContext = Dispatchers.IO, isShowLoad: Boolean = true)  {
        viewModelScope.launch(context) {
            launch(Dispatchers.Main) { if(isShowLoad) App.startLoad() }
            try {
                val result = job()
                launch(Dispatchers.Main) {
                    resData.value = result
                    if(isShowLoad) App.finishLoad()
                }
            } catch (e: Exception){
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    _error.value = e
                    if(isShowLoad) App.finishLoad()
                }
            }
        }
    }
}