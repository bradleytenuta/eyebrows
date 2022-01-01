package com.ddairy.eyebrows.data

import com.ddairy.eyebrows.util.InstanceMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Assert
import org.junit.Test

class ParticipantTest {

    @Test
    fun canSerialize() {
        val participant = Participant(name = "bob")
        val serialized = InstanceMapper.mapper.writeValueAsString(participant)
        val json = """{"name":"bob"}"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canDeserialize() {
        val json = """{"name":"bob"}"""
        val participant = Participant(name = "bob")
        val participant2: Participant = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(participant.name, participant2.name)
    }
}