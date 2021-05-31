package com.depromeet.zerowaste.data.mission

import com.depromeet.zerowaste.data.Theme

data class MissionTag(
    val theme: Theme,
    var selected: Boolean = false
)
