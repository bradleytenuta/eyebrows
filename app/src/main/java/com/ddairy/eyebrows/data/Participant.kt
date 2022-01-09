package com.ddairy.eyebrows.data

import androidx.compose.ui.graphics.Color
import com.ddairy.eyebrows.util.helper.GeneralUtil
import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Object holds information about the people taking part within an eyebrow.
 */
data class Participant(
    var name: String
) {

    @JsonIgnore
    private var iconColor: Color? = null

    /**
     * Gets a random color for the participant, this is only used within the UI.
     */
    fun getIconColor(): Color {
        if (iconColor == null) {
            iconColor = GeneralUtil.getRandomColor()
        }
        return iconColor as Color
    }
}