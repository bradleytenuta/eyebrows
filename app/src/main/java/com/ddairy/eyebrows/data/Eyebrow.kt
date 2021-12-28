package com.ddairy.eyebrows.data

import java.time.LocalDateTime
import java.util.UUID

data class Eyebrow(
    var id: UUID = UUID.randomUUID(), // Since the user may generate identical tasks, give them each a unique ID
    var description: String,
    var prize: String = "",
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endDate: LocalDateTime = LocalDateTime.now().plusDays(1),
    var status: Status = Status.Open,
    var participants: List<Participant> = emptyList()
) {

    enum class Status {
        Open,
        Complete;
    }
}