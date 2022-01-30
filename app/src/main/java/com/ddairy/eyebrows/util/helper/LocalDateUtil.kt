package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import org.joda.time.Days
import org.joda.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime

class LocalDateUtil {

    companion object {

        /**
         * Formats the date into a string format.
         */
        fun getDateAsString(localDate: LocalDate, pattern: String): String {
            val date: java.time.LocalDate =
                java.time.LocalDate.of(localDate.year, localDate.monthOfYear, localDate.dayOfMonth)
            return date.format(DateTimeFormatter.ofPattern(pattern))
        }

        /**
         * Gets the number of days left till the finish date of an eyebrow is reached.
         */
        @ExperimentalTime
        fun getNumberOfDaysTillEndDate(eyebrow: Eyebrow, startDate: LocalDate): Int {
            return Days.daysBetween(startDate, eyebrow.endDate).days;
        }
    }
}