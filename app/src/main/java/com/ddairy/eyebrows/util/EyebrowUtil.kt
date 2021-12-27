package com.ddairy.eyebrows.util

import com.ddairy.eyebrows.data.Eyebrow
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

class EyebrowUtil {

    companion object {

        private val compareDate =  Comparator<Eyebrow> { eyebrowOne, eyebrowTwo ->
            when {
                (getEpoch(eyebrowOne.endDate) >= getEpoch(eyebrowTwo.endDate)) -> 1
                else -> -1
            }
        }

        fun organiseList(eyebrows: List<Eyebrow>): List<Eyebrow> {
            var allOpenItems = eyebrows.filter { it.status == Eyebrow.Status.Open }
            var allCompleteItems = eyebrows.filter { it.status == Eyebrow.Status.Complete }

            return allOpenItems.sortedWith(compareDate).plus(allCompleteItems.sortedWith(compareDate))
        }

        fun getPercentageOfTimeTillEndDate(eyebrow: Eyebrow): Float {
            val currentDateLong = LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond()
            val startDateLong = eyebrow.startDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val endDateLong = eyebrow.endDate.atZone(ZoneOffset.UTC).toEpochSecond()

            val diffDate = endDateLong - startDateLong;
            val currentDiff = currentDateLong - startDateLong;

            return currentDiff.toFloat() / diffDate * 100
        }

        fun getNumberOfDaysTillEndDate(eyebrow: Eyebrow): Long {
            val startDateLong = eyebrow.startDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val endDateLong = eyebrow.endDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val diffDate = endDateLong - startDateLong;

            return TimeUnit.DAYS.convert(diffDate, TimeUnit.SECONDS)
        }

        fun isEndDateInThePast(eyebrow: Eyebrow): Boolean {
            return getPercentageOfTimeTillEndDate(eyebrow) > 1.0f
        }

        fun isLate(eyebrow: Eyebrow): Boolean {
            return isEndDateInThePast(eyebrow) && eyebrow.status == Eyebrow.Status.Open
        }

        private fun getEpoch(date: LocalDateTime): Long {
            return date.atZone(ZoneOffset.UTC).toEpochSecond()
        }
    }
}