package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

class EyebrowUtil {

    companion object {

        /**
         * A compare function that compares two dates to see which one occurs first.
         */
        private val compareDate =  Comparator<Eyebrow> { eyebrowOne, eyebrowTwo ->
            when {
                (getEpoch(eyebrowOne.endDate) >= getEpoch(eyebrowTwo.endDate)) -> 1
                else -> -1
            }
        }

        /**
         * Organises the list of eyebrow objects.
         * Filters the list into two based on the status of the eyebrow.
         * It then sorts the eyebrows in each list based on the finish dates. Eyebrow that finishes soon,
         * will appear higher on the list.
         */
        fun organiseList(eyebrows: List<Eyebrow>): List<Eyebrow> {
            val allOpenItems = eyebrows.filter { it.status == Eyebrow.Status.Open }
            val allCompleteItems = eyebrows.filter { it.status == Eyebrow.Status.Complete }

            return allOpenItems.sortedWith(compareDate).plus(allCompleteItems.sortedWith(compareDate))
        }

        /**
         * Gets a percentage of how long is left till the finish date of an eyebrow is reached.
         */
        fun getPercentageOfTimeTillEndDate(eyebrow: Eyebrow): Float {
            val currentDateLong = LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond()
            val startDateLong = eyebrow.startDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val endDateLong = eyebrow.endDate.atZone(ZoneOffset.UTC).toEpochSecond()

            val diffDate = endDateLong - startDateLong
            val currentDiff = currentDateLong - startDateLong

            return currentDiff.toFloat() / diffDate * 100
        }

        /**
         * Gets the number of days left till the finish date of an eyebrow is reached.
         */
        fun getNumberOfDaysTillEndDate(eyebrow: Eyebrow): Long {
            val startDateLong = eyebrow.startDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val endDateLong = eyebrow.endDate.atZone(ZoneOffset.UTC).toEpochSecond()
            val diffDate = endDateLong - startDateLong

            return TimeUnit.DAYS.convert(diffDate, TimeUnit.SECONDS)
        }

        /**
         * Checks if the end date for a given eyebrow is in the past or not.
         */
        fun isEndDateInThePast(eyebrow: Eyebrow): Boolean {
            return getPercentageOfTimeTillEndDate(eyebrow) > 1.0f
        }

        /**
         * If the end date is in the past and the eyebrow is still in the open status then
         * the eyebrow is considered late.
         */
        fun isLate(eyebrow: Eyebrow): Boolean {
            return isEndDateInThePast(eyebrow) && eyebrow.status == Eyebrow.Status.Open
        }

        /**
         * Verifies that the values are valid to be used within an eyebrow object.
         */
        fun verifyInputs(description: String, startDate: LocalDateTime, endDate: LocalDateTime): Boolean {
            // Description cannot be empty.
            if (description.trim().isEmpty()) {
                return false
            }
            // End date cannot be before the start date
            if (endDate.isBefore(startDate)) {
                return false
            }
            return true
        }

        /**
         * Gets the date in EPOCH time in seconds.
         */
        private fun getEpoch(date: LocalDateTime): Long {
            return date.atZone(ZoneOffset.UTC).toEpochSecond()
        }
    }
}