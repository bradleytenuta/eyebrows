package com.ddairy.eyebrows.util.helper

import com.ddairy.eyebrows.data.Eyebrow
import org.junit.Test

class LocalDateTimeUtilTest {

    @Test
    fun doesOrganiseListInCorrectOrder() {
        val eyebrow = Eyebrow(description = "")
        println(eyebrow.startDate.year)
        println(eyebrow.startDate.monthValue)
        println(eyebrow.startDate.dayOfMonth)
        println("")
    }
}