package com.example.common

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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

    fun convertMillisToDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.getDefault())
        return dateFormat.format(Date(millis))
    }

    fun parseDateStringToMillis(dateString: String): Long {
        return if (dateString.isBlank()) {
            System.currentTimeMillis()
        } else {
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.KOREA)
            return dateFormat.parse(dateString)?.time ?: System.currentTimeMillis()
        }
    }

    fun formatDateTimeToIso8601(date: String, time: String): String {
        val combinedStr = "$date $time"

        val inputFormat = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E) a h:mm", Locale.KOREAN)
        val localDateTime = LocalDateTime.parse(combinedStr, inputFormat)

        val seoulZone = ZoneId.of("Asia/Seoul")
        val utcZone = ZoneId.of("UTC")
        val zonedDateTime = localDateTime.atZone(seoulZone).withZoneSameInstant(utcZone)

        val isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val isoString = zonedDateTime.format(isoFormat)
        return isoString
    }

    fun formatIsoToRegularDate(isoString: String): String {
        val localDateTime = LocalDateTime.parse(isoString)
        val seoulZone = ZoneId.of("Asia/Seoul")
        val zonedDateTime = localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(seoulZone)
        val outputFormatter = DateTimeFormatter.ofPattern("M/dd (E) a h:mm", Locale.KOREAN)
        return zonedDateTime.format(outputFormatter)
    }
}
