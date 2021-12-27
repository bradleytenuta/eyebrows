package com.ddairy.eyebrows.util;

import com.ddairy.eyebrows.data.Eyebrow
import org.junit.Assert
import org.junit.Test;
import java.time.LocalDateTime

class EyebrowUtilTest {

    @Test
    fun doesOrganiseListInCorrectOrder() {
        val listBefore = listOf(
            Eyebrow(endDate = LocalDateTime.now().plusDays(2)),
            Eyebrow(endDate = LocalDateTime.now().plusDays(3)),
            Eyebrow(endDate = LocalDateTime.now())
        )

        val listAfter = EyebrowUtil.organiseList(listBefore)

        Assert.assertEquals(listAfter[0], listBefore[2])
        Assert.assertEquals(listAfter[1], listBefore[0])
        Assert.assertEquals(listAfter[2], listBefore[1])
    }

    @Test
    fun doesOrganiseListInCorrectOrderWithDifferentStates() {
        val listBefore = listOf(
            Eyebrow(endDate = LocalDateTime.now().plusDays(2), status = Eyebrow.Status.Complete),
            Eyebrow(endDate = LocalDateTime.now(), status = Eyebrow.Status.Complete),
            Eyebrow(endDate = LocalDateTime.now().plusDays(2)),
            Eyebrow(endDate = LocalDateTime.now().plusDays(3)),
            Eyebrow(endDate = LocalDateTime.now())
        )

        val listAfter = EyebrowUtil.organiseList(listBefore)

        Assert.assertEquals(listAfter[0], listBefore[4])
        Assert.assertEquals(listAfter[1], listBefore[2])
        Assert.assertEquals(listAfter[2], listBefore[3])
        Assert.assertEquals(listAfter[3], listBefore[1])
        Assert.assertEquals(listAfter[4], listBefore[0])
    }
}
