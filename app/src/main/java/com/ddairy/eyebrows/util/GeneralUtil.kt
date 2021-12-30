package com.ddairy.eyebrows.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class GeneralUtil {

    companion object {
        fun getRandomColor(): Color {
            return Color(Random.nextInt(200, 250), Random.nextInt(130, 180), 0)
        }
    }
}