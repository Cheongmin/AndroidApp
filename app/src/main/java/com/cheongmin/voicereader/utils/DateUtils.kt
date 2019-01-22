package com.cheongmin.voicereader.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
  fun getDateString(timeStamp: Long): String {
    val date = Calendar.getInstance()
    date.time = Date(timeStamp * 1000)

    val dateInMillis = date.timeInMillis

    val now = Calendar.getInstance()
    val nowInMillis = now.timeInMillis

    val diffSeconds = (nowInMillis - dateInMillis) / 1000
    return when {
      diffSeconds > 86400 -> SimpleDateFormat("M월 dd일").format(date.time)
      diffSeconds > 3600 -> "${diffSeconds / 60 / 60}시간 전"
      diffSeconds > 60 -> "${diffSeconds / 60}분 전"
      else -> "${diffSeconds}초 전"
    }
  }
}