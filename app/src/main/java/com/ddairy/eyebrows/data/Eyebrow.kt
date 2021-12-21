package com.ddairy.eyebrows.data

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

data class Eyebrow(
    var id: UUID = UUID.randomUUID(), // Since the user may generate identical tasks, give them each a unique ID
    var description: String = "",
    var prize: String = "",
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endDate: LocalDateTime = LocalDateTime.now().plusDays(1)
) {
    fun getStartDateAsString(): String {
        return formatDate(startDate)
    }

    fun getEndDateAsString(): String {
        return formatDate(endDate)
    }

    fun getTimeTillEndDate(): Float {
        val currentDateLong = LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond()
        val startDateLong = startDate.atZone(ZoneOffset.UTC).toEpochSecond()
        val endDateLong = endDate.atZone(ZoneOffset.UTC).toEpochSecond()

        val diffDate = endDateLong - startDateLong;
        val currentDiff = currentDateLong - startDateLong;

        return currentDiff.toFloat() / diffDate * 100
    }

    private fun formatDate(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}