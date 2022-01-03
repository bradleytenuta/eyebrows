package com.ddairy.eyebrows.ui.home.components.card.bottom

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ddairy.eyebrows.data.Eyebrow

/**
 * The UI for the bottom part of the eyebrow card.
 */
@Composable
fun CardBottom(
    eyebrow: Eyebrow,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    onClickNewEyebrows: (Eyebrow) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ParticipantSection(eyebrow = eyebrow)
        ActionButtonSection(
            eyebrow = eyebrow,
            removeEyebrow = removeEyebrow,
            updateEyebrow = updateEyebrow,
            onClickNewEyebrows = onClickNewEyebrows
        )
    }
}