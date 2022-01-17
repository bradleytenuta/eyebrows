package com.ddairy.eyebrows.data

import org.joda.time.LocalDate
import java.util.UUID

/**
 * The object used to hold the eyebrow information.
 */
data class Eyebrow(
    var description: String,
    var id: UUID = UUID.randomUUID(), // Since the user may generate identical tasks, give them each a unique ID
    var endDate: LocalDate = LocalDate.now(),
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