package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class LocalDateTimeUtil {

    companion object {

        /**
         * Formats the date into a string format.
         */
        fun getDateAsString(localDateTime: LocalDateTime, pattern: String): String {
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern))
        }

        /**
         * Gets the number of days left till the finish date of an eyebrow is reached.
         */
        fun getNumberOfDaysTillEndDate(eyebrow: Eyebrow, startDate: LocalDateTime): Int {
            val startDateLong = startDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val endDateLong = eyebrow.endDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val diffDate = endDateLong - startDateLong

            return TimeUnit.DAYS.convert(diffDate, TimeUnit.SECONDS).toInt()
        }
    }
}