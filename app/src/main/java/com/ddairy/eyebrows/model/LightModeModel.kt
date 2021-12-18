package com.ddairy.eyebrows.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// TODO: https://developer.android.com/topic/libraries/architecture/livedata and https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate
class LightModeModel : ViewModel() {

    private var isLight: Boolean by mutableStateOf(true)

    fun isLightMode(): Boolean {
        return this.isLight
    }

    fun toggleLightMode(
        isLight: Boolean = !this.isLight
    ) {
        this.isLight = isLight
    }
}