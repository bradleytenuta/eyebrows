package com.ddairy.eyebrows.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ModelLightMode : ViewModel() {

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