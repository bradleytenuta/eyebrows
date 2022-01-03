package com.ddairy.eyebrows.util.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeUtil {

    companion object {

        /**
         * Formats the date into a string format.
         */
        fun getDateAsString(localDateTime: LocalDateTime): String {
            return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }
}