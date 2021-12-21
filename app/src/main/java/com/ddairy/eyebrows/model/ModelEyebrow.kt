package com.ddairy.eyebrows.model;

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ddairy.eyebrows.data.Eyebrow

class ModelEyebrow : ViewModel() {

    var eyebrows = mutableStateListOf<Eyebrow>()
    private set // By specifying private set, we're restricting writes to this state object to a private setter only visible inside the ViewModel.

    fun addEyebrow(eyebrow: Eyebrow) {
        // If an eyebrow with the same id already exists, then replace it.
        eyebrows.removeIf { eyebrowItem -> eyebrowItem.id == eyebrow.id }
        eyebrows.add(eyebrow)
    }

    fun removeEyebrow(eyebrow: Eyebrow) {
        eyebrows.remove(eyebrow)
    }
}