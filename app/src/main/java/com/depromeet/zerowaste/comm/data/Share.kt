package com.depromeet.zerowaste.comm.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.depromeet.zerowaste.BuildConfig


// 동적 객체 공유
object Share {
    var authToken = ""
}