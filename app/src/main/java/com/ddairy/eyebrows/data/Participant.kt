package com.ddairy.eyebrows.data

import androidx.compose.ui.graphics.Color
import com.ddairy.eyebrows.util.GeneralUtil

data class Participant(
    var name: String
) {

    var iconColor: Color? = null

    fun getIconColor(): Color {
        if (iconColor == null) {
            iconColor = GeneralUtil.getRandomColor()
        }
        return iconColor as Color
    }
}