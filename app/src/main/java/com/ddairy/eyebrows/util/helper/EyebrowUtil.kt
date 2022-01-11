package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import java.time.LocalDate
import java.time.ZoneOffset

class EyebrowUtil {

    companion object {

        /**
         * Organises the list of eyebrow objects.
         * Filters the list into two based on the status of the eyebrow.
         * It then sorts the eyebrows in each list based on the finish dates. Eyebrow that finishes soon,
         * will appear higher on the list.
         */
        fun organiseList(eyebrows: List<Eyebrow>): List<Eyebrow> {
            val allOpenItems = eyebrows.filter { it.status == Eyebrow.Status.Open }
            val allCompleteItems = eyebrows.filter { it.status == Eyebrow.Status.Complete }
            return allOpenItems.sortedWith(compareBy({ it.endDate }, { it.endDate })).plus(allCompleteItems.sortedWith(compareBy({ it.endDate }, { it.endDate })))
        }

        /**
         * Verifies that the description value for an eyebrow object is valid.
         */
        fun isDescriptionValid(description: String): Boolean {
            return description.trim().isNotEmpty()
        }

        /**
         * Verifies that the start and end date values for an eyebrow object is valid.
         */
        fun isDateValid(startDate: LocalDate, endDate: LocalDate): Boolean {
            return endDate.isAfter(startDate) || endDate.isEqual(startDate)
        }
    }
}