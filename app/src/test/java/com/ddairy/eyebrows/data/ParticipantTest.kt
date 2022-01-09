package com.ddairy.eyebrows.data

import org.junit.Assert
import org.junit.Test

class ParticipantTest {

    @Test
    fun canGetRandomColor() {
        Assert.assertNotNull(Participant(name = "Bob").getIconColor())
    }
}