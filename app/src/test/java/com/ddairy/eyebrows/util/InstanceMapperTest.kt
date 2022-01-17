package com.ddairy.eyebrows.util

import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.data.Preferences
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Assert
import org.junit.Test
import org.joda.time.LocalDate
import java.util.*

class InstanceMapperTest {

    @Test
    fun canSerializeEyebrows() {
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            endDate = LocalDate(2020, 1, 2),
            participants = listOf(Participant(name = "bob"))
        )
        val serialized = InstanceMapper.mapper.writeValueAsString(eyebrow)
        val json = """{"description":"description here","id":"1802e58c-09b8-40b6-a284-b74a3825abcb","endDate":"2020-01-02","status":"Open","participants":[{"name":"bob"}]}"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canSerializeArray() {
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            endDate = LocalDate(2020, 1, 2),
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrows = listOf(eyebrow, eyebrow)

        val serialized = InstanceMapper.mapper.writeValueAsString(eyebrows)
        val json = """[{"description":"description here","id":"1802e58c-09b8-40b6-a284-b74a3825abcb","endDate":"2020-01-02","status":"Open","participants":[{"name":"bob"}]},{"description":"description here","id":"1802e58c-09b8-40b6-a284-b74a3825abcb","endDate":"2020-01-02","status":"Open","participants":[{"name":"bob"}]}]"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canDeserializeEyebrows() {
        val json = """{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","endDate":"2020-01-02","status":"Open","participants":[{"name":"bob"}]}"""
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            endDate = LocalDate(2020, 1, 2),
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrow2: Eyebrow = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(eyebrow, eyebrow2)
    }

    @Test
    fun canDeserializeWithCompleteState() {
        val json = """{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","endDate":"2020-01-02","status":"Complete","participants":[{"name":"bob"}]}"""
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            endDate = LocalDate(2020, 1, 2),
            status = Eyebrow.Status.Complete,
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrow2: Eyebrow = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(eyebrow, eyebrow2)
    }

    @Test
    fun canDeserializeListOfEyebrows() {
        val json = """[{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","endDate":"2020-01-02","status":"Open","participants":[{"name":"bob"}]},{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","endDate":"2020-01-02","status":"Open","participants":[{"name":"bob"}]}]"""
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            endDate = LocalDate(2020, 1, 2),
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrows = listOf(eyebrow, eyebrow)

        val eyebrows2: List<Eyebrow> = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(eyebrows, eyebrows2)
    }

    @Test
    fun canSerializePreferences() {
        val preferences = Preferences(
            showWelcomeScreen = true
        )
        val serialized = InstanceMapper.mapper.writeValueAsString(preferences)
        val json = """{"showWelcomeScreen":true}"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canDeserializePreferences() {
        val json = """{"showWelcomeScreen":true}"""
        val preferences = Preferences(
            showWelcomeScreen = true
        )
        val preferences2: Preferences = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(preferences, preferences2)
    }

    @Test
    fun canSerializeParticipant() {
        val participant = Participant(name = "bob")
        val serialized = InstanceMapper.mapper.writeValueAsString(participant)
        val json = """{"name":"bob"}"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canDeserializeParticipant() {
        val json = """{"name":"bob"}"""
        val participant = Participant(name = "bob")
        val participant2: Participant = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(participant.name, participant2.name)
    }
}