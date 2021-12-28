package com.ddairy.eyebrows.util

import androidx.compose.ui.graphics.Color
import java.util.Random

class GeneralUtil {

    companion object {
        fun getRandomColor(): Color {
            // TODO: Maybe don't do random color as it changes all the time, maybe use a mixture of the 4 saved colors.
            val rnd = Random()
            return Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }
    }
}