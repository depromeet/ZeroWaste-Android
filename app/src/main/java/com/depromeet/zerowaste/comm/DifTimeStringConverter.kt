package com.depromeet.zerowaste.comm

class DifTimeStringConverter {

    companion object {
        private const val SECOND: Long = 1000
        private const val MINUTE: Long = SECOND * 60
        private const val HOUR: Long = MINUTE * 60
        private const val DAY: Long = HOUR * 24

        fun convert(start: Long, end: Long): String {
            val builder = StringBuilder()
            val dif = end - start
            if(dif < 0) builder.append("-")
            val day = dif / DAY
            var remain = dif % DAY
            if(day > 0) builder.append("${day}일 ")
            val hour = remain / HOUR
            remain %= HOUR
            if(hour > 0) builder.append("${hour}시간 ")
            val min = remain / MINUTE
            remain %= MINUTE
            if(min > 0) builder.append("${min}분")
            else {
                val sec = remain / SECOND
                builder.append("${sec}초")
            }

            return builder.toString()
        }
    }
}