package com.ddairy.eyebrows.ui.home.components.card

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
import com.ddairy.eyebrows.util.helper.EyebrowUtil

/**
 * The UI for the eyebrow card progress bar.
 */
@Composable
fun ProgressContainer(
    eyebrow: Eyebrow,
    surfaceModifier: Modifier = Modifier
) {
    Surface(modifier = surfaceModifier) {
        // TODO: Currently a value within LinearProgressIndicator is statically set
        // TODO: meaning that the width is minimum 240.dp, hopefully this gets fixed.
        LinearProgressIndicator(
            progress = if (eyebrow.status == Eyebrow.Status.Complete) {
                1.0f
            } else {
                if (EyebrowUtil.isEndDateInThePast(eyebrow)) 1.0f else EyebrowUtil.getPercentageOfTimeTillEndDate(eyebrow)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 5.dp
                ),
            color = if (EyebrowUtil.isLate(eyebrow)) MaterialTheme.colors.error else MaterialTheme.colors.primary
        )

        val daysLeft = EyebrowUtil.getNumberOfDaysTillEndDate(eyebrow)
        var progressText = if (eyebrow.status == Eyebrow.Status.Complete) {
            "Completed!"
        } else if (daysLeft == 0L) {
            "Time's up!"
        } else if (daysLeft == 1L) {
            "$daysLeft day"
        } else {
            "$daysLeft days"
        }

        if (EyebrowUtil.isLate(eyebrow)) {
            progressText = "$progressText late"
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