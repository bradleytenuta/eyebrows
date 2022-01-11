package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class EyebrowUtilTest {

    @Test
    fun doesOrganiseListInCorrectOrder() {
        val listBefore = listOf(
            Eyebrow(description = "", endDate = LocalDate.now().plusDays(2)),
            Eyebrow(description = "", endDate = LocalDate.now().plusDays(3)),
            Eyebrow(description = "", endDate = LocalDate.now())
        )

        val listAfter = EyebrowUtil.organiseList(listBefore)

        Assert.assertEquals(listAfter[0], listBefore[2])
        Assert.assertEquals(listAfter[1], listBefore[0])
        Assert.assertEquals(listAfter[2], listBefore[1])
    }

    @Test
    fun doesOrganiseListInCorrectOrderWithDifferentStates() {
        val listBefore = listOf(
            Eyebrow(description = "", endDate = LocalDate.now().plusDays(2), status = Eyebrow.Status.Complete),
            Eyebrow(description = "", endDate = LocalDate.now(), status = Eyebrow.Status.Complete),
            Eyebrow(description = "", endDate = LocalDate.now().plusDays(2)),
            Eyebrow(description = "", endDate = LocalDate.now().plusDays(3)),
            Eyebrow(description = "", endDate = LocalDate.now())
        )

        val listAfter = EyebrowUtil.organiseList(listBefore)

        Assert.assertEquals(listAfter[0], listBefore[4])
        Assert.assertEquals(listAfter[1], listBefore[2])
        Assert.assertEquals(listAfter[2], listBefore[3])
        Assert.assertEquals(listAfter[3], listBefore[1])
        Assert.assertEquals(listAfter[4], listBefore[0])
    }

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
        val startDate = LocalDate.of(2020, 1, 1)
        val endDate = LocalDate.of(2020, 1, 2)
        Assert.assertTrue(EyebrowUtil.isDateValid(startDate, endDate))
    }

    @Test
    fun doesReturnValidDateIfSameDay() {
        val startDate = LocalDate.of(2020, 1, 1)
        val endDate = LocalDate.of(2020, 1, 1)
        Assert.assertTrue(EyebrowUtil.isDateValid(startDate, endDate))
    }

    @Test
    fun shouldFailIfInvalidDate() {
        val startDate = LocalDate.of(2020, 1, 2)
        val endDate = LocalDate.of(2020, 1, 1)
        Assert.assertFalse(EyebrowUtil.isDateValid(startDate, endDate))
    }
}
