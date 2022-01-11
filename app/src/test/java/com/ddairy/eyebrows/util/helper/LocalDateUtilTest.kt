package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import kotlin.time.ExperimentalTime

class LocalDateUtilTest {

    @Test
    fun doesGetDateInRightFormat() {
        val date = LocalDate.of(2020, 1, 1)
        val dateAsString = LocalDateUtil.getDateAsString(date, "dd/MM/yyyy")
        Assert.assertEquals("01/01/2020", dateAsString)
    }

    @ExperimentalTime
    @Test
    fun doesGetCorrectNumberOfDaysTillEnd() {
        val startDate = LocalDate.of(2020, 1, 1)
        val eyebrow = Eyebrow(
            description = "",
            endDate = LocalDate.of(2020, 1, 2)
        )

        val numberOfDaysTillEnd = LocalDateUtil.getNumberOfDaysTillEndDate(eyebrow, startDate)
        Assert.assertEquals(1, numberOfDaysTillEnd)
    }

    @ExperimentalTime
    @Test
    fun doesGetCorrectNumberOfDaysTillEndFromThePast() {
        val startDate = LocalDate.of(2020, 1, 2)
        val eyebrow = Eyebrow(
            description = "",
            endDate = LocalDate.of(2020, 1, 1)
        )

        val numberOfDaysTillEnd = LocalDateUtil.getNumberOfDaysTillEndDate(eyebrow, startDate)
        Assert.assertEquals(-1, numberOfDaysTillEnd)
    }

    @ExperimentalTime
    @Test
    fun doesGetCorrectNumberOfDaysTillEndZero() {
        val startDate = LocalDate.of(2020, 1, 1)
        val eyebrow = Eyebrow(
            description = "",
            endDate = LocalDate.of(2020, 1, 1)
        )

        val numberOfDaysTillEnd = LocalDateUtil.getNumberOfDaysTillEndDate(eyebrow, startDate)
        Assert.assertEquals(0, numberOfDaysTillEnd)
    }
}