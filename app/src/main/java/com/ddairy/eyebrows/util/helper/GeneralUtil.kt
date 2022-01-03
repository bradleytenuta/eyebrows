package com.ddairy.eyebrows.util.helper

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

class GeneralUtil {

    companion object {

        /**
         * Values of the different RGB ranges.
         */
        private const val RED_LOWER = 200
        private const val RED_HIGHER = 250
        private const val GREEN_LOWER = 130
        private const val GREEN_HIGHER = 180
        private const val BLUE_VALUE = 0

        /**
         * Gets a random color object based on RGB values that are in a range similar
         * to the primary and secondary colors used.
         */
        fun getRandomColor(): Color {
            return Color(Random.nextInt(RED_LOWER, RED_HIGHER), Random.nextInt(GREEN_LOWER, GREEN_HIGHER), BLUE_VALUE)
        }
    }
}