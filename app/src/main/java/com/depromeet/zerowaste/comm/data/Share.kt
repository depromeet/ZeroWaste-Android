package com.depromeet.zerowaste.comm.data

import com.depromeet.zerowaste.data.auth.User

// 동적 객체 공유
object Share {
    var authToken = ""

    var user: User? = null
}