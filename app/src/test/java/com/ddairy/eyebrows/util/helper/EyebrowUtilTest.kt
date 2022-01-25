package com.ddairy.eyebrows.util.helper

import org.junit.Assert
import org.junit.Test
import org.joda.time.LocalDate

class EyebrowUtilTest {

    @Test
    fun doesReturnValidDescription() {
        Assert.assertTrue(EyebrowUtil.isDescriptionValid("test"))
    }

    @Test
    fun shouldFailIfInvalidDescription() {
        Assert.assertFalse(EyebrowUtil.isDescriptionValid(""))
    }

    @Test
    fun doesReturnValidDate() {
        val startDate = LocalDate(2020, 1, 1)
        val endDate = LocalDate(2020, 1, 2)
        Assert.assertTrue(EyebrowUtil.isDateValid(startDate, endDate))
    }

    @Test
    fun doesReturnValidDateIfSameDay() {
        val startDate = LocalDate(2020, 1, 1)
        val endDate = LocalDate(2020, 1, 1)
        Assert.assertTrue(EyebrowUtil.isDateValid(startDate, endDate))
    }

    @Test
    fun shouldFailIfInvalidDate() {
        val startDate = LocalDate(2020, 1, 2)
        val endDate = LocalDate(2020, 1, 1)
        Assert.assertFalse(EyebrowUtil.isDateValid(startDate, endDate))
    }
}
