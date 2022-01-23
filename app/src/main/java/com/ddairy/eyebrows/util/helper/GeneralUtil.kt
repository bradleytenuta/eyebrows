package com.ddairy.eyebrows.util.helper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.ddairy.eyebrows.ui.theme.Secondary
import com.ddairy.eyebrows.ui.theme.SecondaryVariant
import kotlin.random.Random

class GeneralUtil {

    companion object {

        /**
         * The default version name of the app. This value should be updated to the
         * new version name when the app starts.
         */
        private const val defaultVersionName = "1.0"
        var versionName: String = defaultVersionName

        /**
         * Values of the different RGB ranges.
         */
        private var RED_LOWER = Secondary.toArgb().red
        private var GREEN_LOWER = SecondaryVariant.toArgb().green
        private var BLUE_LOWER = SecondaryVariant.toArgb().blue

        private var RED_HIGHER = SecondaryVariant.toArgb().red
        private var GREEN_HIGHER = Secondary.toArgb().green
        private var BLUE_HIGHER = Secondary.toArgb().blue

        /**
         * Gets a random color object based on RGB values that are in a range similar
         * to the primary and secondary colors used.
         */
        fun getRandomColor(): Color {
            return Color(
                Random.nextInt(RED_LOWER, RED_HIGHER),
                Random.nextInt(GREEN_LOWER, GREEN_HIGHER),
                Random.nextInt(BLUE_LOWER, BLUE_HIGHER)
            )
        }
    }
}