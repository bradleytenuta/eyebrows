package com.ddairy.eyebrows.util.helper

import org.joda.time.LocalDate

class EyebrowUtil {

    companion object {

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