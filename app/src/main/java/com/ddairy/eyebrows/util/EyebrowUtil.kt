package com.ddairy.eyebrows.util

import com.ddairy.eyebrows.data.Eyebrow
import java.time.LocalDateTime
import java.time.ZoneOffset

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

        private fun getEpoch(date: LocalDateTime): Long {
            return date.atZone(ZoneOffset.UTC).toEpochSecond()
        }
    }
}