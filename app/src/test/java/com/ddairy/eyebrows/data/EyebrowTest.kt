package com.ddairy.eyebrows.data

import com.ddairy.eyebrows.util.InstanceMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.util.*

class EyebrowTest {

    @Test
    fun canSerialize() {
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            prize = "£100",
            startDate = LocalDateTime.of(2020, 1, 1, 12, 0),
            endDate = LocalDateTime.of(2020, 1, 2, 12, 0),
            participants = listOf(Participant(name = "bob"))
        )
        val serialized = InstanceMapper.mapper.writeValueAsString(eyebrow)
        val json = """{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","prize":"£100","startDate":"2020-01-01T12:00:00","endDate":"2020-01-02T12:00:00","status":"Open","participants":[{"name":"bob"}]}"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canSerializeArray() {
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            prize = "£100",
            startDate = LocalDateTime.of(2020, 1, 1, 12, 0),
            endDate = LocalDateTime.of(2020, 1, 2, 12, 0),
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrows = listOf(eyebrow, eyebrow)

        val serialized = InstanceMapper.mapper.writeValueAsString(eyebrows)
        val json = """[{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","prize":"£100","startDate":"2020-01-01T12:00:00","endDate":"2020-01-02T12:00:00","status":"Open","participants":[{"name":"bob"}]},{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","prize":"£100","startDate":"2020-01-01T12:00:00","endDate":"2020-01-02T12:00:00","status":"Open","participants":[{"name":"bob"}]}]"""
        Assert.assertEquals(serialized, json)
    }

    @Test
    fun canDeserialize() {
        val json = """{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","prize":"£100","startDate":"2020-01-01T12:00:00","endDate":"2020-01-02T12:00:00","status":"Open","participants":[{"name":"bob"}]}"""
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            prize = "£100",
            startDate = LocalDateTime.of(2020, 1, 1, 12, 0),
            endDate = LocalDateTime.of(2020, 1, 2, 12, 0),
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrow2: Eyebrow = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(eyebrow, eyebrow2)
    }

    @Test
    fun canDeserializeWithCompleteState() {
        val json = """{"id":"1802e58c-09b8-40b6-a284-b74a3825abcb","description":"description here","prize":"£100","startDate":"2020-01-01T12:00:00","endDate":"2020-01-02T12:00:00","status":"Complete","participants":[{"name":"bob"}]}"""
        val eyebrow = Eyebrow(
            id = UUID.fromString("1802e58c-09b8-40b6-a284-b74a3825abcb"),
            description = "description here",
            prize = "£100",
            startDate = LocalDateTime.of(2020, 1, 1, 12, 0),
            endDate = LocalDateTime.of(2020, 1, 2, 12, 0),
            status = Eyebrow.Status.Complete,
            participants = listOf(Participant(name = "bob"))
        )
        val eyebrow2: Eyebrow = InstanceMapper.mapper.readValue(json)
        Assert.assertEquals(eyebrow, eyebrow2)
    }
}