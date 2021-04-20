package com.whiteside.cafe.utils

class Time {

    companion object {
        fun timeStampToCountDown(timeInMills: Long): String {
            var seconds = timeInMills

            val days = seconds / 86400000
            seconds %= 86400000

            val hours = seconds / 3600000
            seconds %= 60000

            val miens = seconds / 60000

            return "$days days $hours hours $miens Miens"
        }
    }
}