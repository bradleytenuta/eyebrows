package com.ddairy.eyebrows.ui.components.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddairy.eyebrows.data.Eyebrow

@Composable
fun ProgressContainer(
    eyebrow: Eyebrow,
    surfaceModifier: Modifier = Modifier
) {
    Surface(modifier = surfaceModifier) {
        // TODO: Currently a value within LinearProgressIndicator is statically set
        // TODO: meaning that the width is minimum 240.dp, hopefully this gets fixed.
        LinearProgressIndicator(
            progress = eyebrow.getPercentageOfTimeTillEndDate(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp
                )
        )

        val daysLeft = eyebrow.getNumberOfDaysTillEndDate()
        val progressText = if (daysLeft == 0L) {
            "Time's up"
        } else if (daysLeft == 1L) {
            "$daysLeft day"
        } else {
            "$daysLeft days"
        }

        Text(
            text = progressText,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}