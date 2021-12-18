package com.ddairy.eyebrows.model;

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.*

data class Eyebrow(
    var id: UUID = UUID.randomUUID(), // Since the user may generate identical tasks, give them each a unique ID
    var title: String = "",
    var description: String = "",
    var prize: String = "",
    var endDate: Date? = null
)

class EyebrowModel : ViewModel() {

    var eyebrows = mutableStateListOf<Eyebrow>()
    private set // By specifying private set, we're restricting writes to this state object to a private setter only visible inside the ViewModel.

    fun addEyebrow(eyebrow: Eyebrow) {
        eyebrows.add(eyebrow)
    }

    fun removeEyebrow(eyebrow: Eyebrow) {
        eyebrows.remove(eyebrow)
    }
}