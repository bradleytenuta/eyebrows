package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class LocalDateTimeUtilTest {

    @Test
    fun doesGetDateInRightFormat() {
        val date = LocalDateTime.of(2020, 1, 1, 0, 0, 0)
        val dateAsString = LocalDateTimeUtil.getDateAsString(date, "dd/MM/yyyy")
        Assert.assertEquals("01/01/2020", dateAsString)
    }

    @Test
    fun doesGetCorrectNumberOfDaysTillEnd() {
        val startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0)
        val eyebrow = Eyebrow(
            description = "",
            endDate = LocalDateTime.of(2020, 1, 2, 0, 0, 0)
        )

        val numberOfDaysTillEnd = LocalDateTimeUtil.getNumberOfDaysTillEndDate(eyebrow, startDate)
        Assert.assertEquals(1, numberOfDaysTillEnd)
    }

    @Test
    fun doesGetCorrectNumberOfDaysTillEndFromThePast() {
        val startDate = LocalDateTime.of(2020, 1, 2, 0, 0, 0)
        val eyebrow = Eyebrow(
            description = "",
            endDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0)
        )

        val numberOfDaysTillEnd = LocalDateTimeUtil.getNumberOfDaysTillEndDate(eyebrow, startDate)
        Assert.assertEquals(-1, numberOfDaysTillEnd)
    }

    @Test
    fun doesGetCorrectNumberOfDaysTillEndZero() {
        val startDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0)
        val eyebrow = Eyebrow(
            description = "",
            endDate = LocalDateTime.of(2020, 1, 1, 0, 0, 0)
        )

        val numberOfDaysTillEnd = LocalDateTimeUtil.getNumberOfDaysTillEndDate(eyebrow, startDate)
        Assert.assertEquals(0, numberOfDaysTillEnd)
    }
}