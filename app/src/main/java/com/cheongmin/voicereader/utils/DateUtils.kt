package com.cheongmin.voicereader.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun toLong(year: Int, month: Int, date: Int, hourOfDay: Int, minute: Int): Long {
        var calendar = Calendar.getInstance()
        calendar.set(year, month, date, hourOfDay, minute)

        return calendar.time.time
    }

    fun toDayText(time: Long): String {
        return SimpleDateFormat("MM월 dd일").format(Date(time))
    }
}