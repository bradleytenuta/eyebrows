package com.ddairy.eyebrows.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeUtil {

    companion object {
        fun getDateAsString(localDateTime: LocalDateTime): String {
            return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }
}