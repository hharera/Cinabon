package com.whiteside.cafe.utils

class Time {

    companion object {
        fun timeStampToCountDown() {
            var millisUntilFinished = millisUntilFinished
            val days = millisUntilFinished / 86400000
            millisUntilFinished = millisUntilFinished % 86400000
            val hours = millisUntilFinished / 3600000
            millisUntilFinished = millisUntilFinished % 60000
            val miens = millisUntilFinished / 60000
            bind.endTime.text = "$days days $hours hours $miens Miens"
        }
    }
}