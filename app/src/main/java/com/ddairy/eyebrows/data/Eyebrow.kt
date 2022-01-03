package com.ddairy.eyebrows.data

import java.time.LocalDateTime
import java.util.UUID

/**
 * The object used to hold the eyebrow information.
 */
data class Eyebrow(
    var id: UUID = UUID.randomUUID(), // Since the user may generate identical tasks, give them each a unique ID
    var description: String,
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endDate: LocalDateTime = LocalDateTime.now().plusDays(1),
    var status: Status = Status.Open,
    var participants: List<Participant> = emptyList()
) {

    /**
     * The status of the eyebrow. It provides information on if the eyebrow is complete or not.
     */
    enum class Status {
        Open,
        Complete;
    }
}