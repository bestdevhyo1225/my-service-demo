package com.hs.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeFormatterUtils {
    companion object {
        private val FORMATTER_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        fun toStringDateTime(localDateTime: LocalDateTime): String = localDateTime.format(FORMATTER_PATTERN)

        fun toLocalDateTime(stringDateTime: String): LocalDateTime =
            LocalDateTime.parse(stringDateTime, FORMATTER_PATTERN)
    }
}
