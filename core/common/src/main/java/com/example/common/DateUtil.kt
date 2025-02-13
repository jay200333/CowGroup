package com.example.common

import java.time.LocalDate
import java.time.ZoneOffset

object DateUtil {
    val todayStartOfDayMillis = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}