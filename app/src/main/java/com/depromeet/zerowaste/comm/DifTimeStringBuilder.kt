package com.depromeet.zerowaste.comm

class DifTimeStringConverter {

    companion object {
        private const val SECOND = 1000
        private const val MINUTE = SECOND * 60
        private const val HOUR = MINUTE * 60
        private const val DAY = HOUR * 24

        fun convert(start: Long, end: Long): String {
            val builder = StringBuilder()
            val dif = end - start
            if(dif < 0) builder.append("-")
            val hour = dif / HOUR
            var remain = dif % HOUR
            if(hour < 10) builder.append("0$hour:") else builder.append("$hour:")
            val min = remain / MINUTE
            remain %= MINUTE
            if(min < 10) builder.append("0$min:") else builder.append("$min:")
            val sec = remain / SECOND
            if(sec < 10) builder.append("0$sec") else builder.append("$sec")

            return builder.toString()
        }
    }
}