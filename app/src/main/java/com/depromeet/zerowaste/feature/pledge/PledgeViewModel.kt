package com.depromeet.zerowaste.feature.pledge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.zerowaste.api.UserApi
import com.depromeet.zerowaste.comm.BaseViewModel
import com.depromeet.zerowaste.comm.data.Share
import com.depromeet.zerowaste.data.user.ReqUpdateUserData

class PledgeViewModel: BaseViewModel() {

    private val _editNickname =  MutableLiveData<String>()
    val editNickname: LiveData<String> get() = _editNickname

    private val _updatePledgeCode = MutableLiveData<Int>()
    val updatePledgeCode: LiveData<Int> get() = _updatePledgeCode

    private val _actionDone = MutableLiveData<Boolean>()
    val actionDone: LiveData<Boolean> get() = _actionDone

    fun setNickname(nickname: String) {
        _editNickname.postValue(nickname)
    }

    fun setActionDone(done: Boolean) {
        _actionDone.postValue(done)
    }

    fun updatePledge() {
        execute({
            val nickname = _editNickname.value ?: return@execute -1
            val code = UserApi.checkNickName(nickname).errorCode
            if(code != 0) return@execute 40001
            val user = Share.user ?: return@execute -1
            val resUpdatedUser = UserApi.updateUserInfo(user.id, ReqUpdateUserData(nickname))
            resUpdatedUser.data?.also {
                Share.user = it.getUser()
                Share.authToken = it.token
            } ?: return@execute -1
            resUpdatedUser.errorCode
        }, _updatePledgeCode)
    }

}