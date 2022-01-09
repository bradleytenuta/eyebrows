package com.ddairy.eyebrows.util.helper

import org.junit.Assert
import org.junit.Test

class GeneralUtilTest {

    @Test
    fun getRandomColor() {
        Assert.assertNotNull(GeneralUtil.getRandomColor())
    }
}