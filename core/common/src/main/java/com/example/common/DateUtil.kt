package com.example.common

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtil {
    val todayStartOfDayMillis =
        LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

    fun timeAgoFromISOString(isoString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = dateFormat.parse(isoString) ?: return "시간 오류"
        val now = Date()
        val differenceInMinutes = (now.time - date.time) / (1000 * 60)
        val differenceInHours = differenceInMinutes / 60
        val differenceInDays = differenceInHours / 24
        val differenceInWeeks = differenceInDays / 7
        val differenceInMonths = differenceInDays / 30
        val differenceInYears = differenceInDays / 365
        return when {
            differenceInMinutes < 1 -> "방금"
            differenceInMinutes < 60 -> "${differenceInMinutes}분 전"
            differenceInHours < 24 -> "${differenceInHours}시간 전"
            differenceInDays < 7 -> "${differenceInDays}일 전"
            differenceInDays < 30 -> "${differenceInWeeks}주 전"
            differenceInMonths < 12 -> "${differenceInMonths}개월 전"
            else -> "${differenceInYears}년 전"
        }
    }
}