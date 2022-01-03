package com.ddairy.eyebrows.ui.home.components.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.util.helper.LocalDateTimeUtil

/**
 * The UI for the main content of the eyebrow card.
 */
@Composable
fun CardContent(eyebrow: Eyebrow) {
    // Custom Modifier for the UI elements above the main divider.
    val modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    // UI for Eyebrow description.
    Text(
        modifier = modifier,
        text = eyebrow.description,
        style = MaterialTheme.typography.h5
    )

    // UI for Eyebrow start and end date.
    Row(modifier = modifier) {
        // UI for start date.
        Icon(
            imageVector = Icons.Filled.Schedule,
            contentDescription = "Start Date"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = LocalDateTimeUtil.getDateAsString(eyebrow.startDate))

        // Progress bar to end date.
        ProgressContainer(
            eyebrow = eyebrow,
            Modifier.weight(1f)
        )

        // UI for end date.
        Icon(
            imageVector = Icons.Filled.Flag,
            contentDescription = "End Date"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = LocalDateTimeUtil.getDateAsString(eyebrow.endDate))
    }
}