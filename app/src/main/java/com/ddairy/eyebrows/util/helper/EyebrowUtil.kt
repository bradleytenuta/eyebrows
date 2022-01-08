package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import java.time.LocalDateTime
import java.time.ZoneOffset

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
         * Verifies that the description value for an eyebrow object is valid.
         */
        fun isDescriptionValid(description: String): Boolean {
            // Description cannot be empty.
            if (description.trim().isEmpty()) {
                return false
            }
            return true
        }

        /**
         * Verifies that the start and end date values for an eyebrow object is valid.
         */
        fun isDateValid(endDate: LocalDateTime): Boolean {
            // End date cannot be before the start date
            if (endDate.isBefore(LocalDateTime.now())) {
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