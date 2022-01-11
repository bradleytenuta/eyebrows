package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime

class LocalDateUtil {

    companion object {

        /**
         * Formats the date into a string format.
         */
        fun getDateAsString(localDate: LocalDate, pattern: String): String {
            return localDate.format(DateTimeFormatter.ofPattern(pattern))
        }

        /**
         * Gets the number of days left till the finish date of an eyebrow is reached.
         */
        @ExperimentalTime
        fun getNumberOfDaysTillEndDate(eyebrow: Eyebrow, startDate: LocalDate): Int {
            return eyebrow.endDate.compareTo(startDate)
        }
    }
}