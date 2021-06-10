package com.depromeet.zerowaste.comm.data

import android.net.Uri
import com.depromeet.zerowaste.data.user.User

// 동적 객체 공유
object Share {
    var authToken = ""                                      // 현재 유저 jwt
    var user: User? = null                                  // 현재 유저 정보
}