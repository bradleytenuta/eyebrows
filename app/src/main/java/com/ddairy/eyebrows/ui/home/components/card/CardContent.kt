package com.ddairy.eyebrows.ui.home.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.util.helper.LocalDateTimeUtil
import kotlin.math.abs

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
    EyebrowText(
        modifier = modifier,
        text = eyebrow.description,
        style = MaterialTheme.typography.h5
    )

    // UI for Eyebrow start and end date.
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EyebrowText(
                    modifier = Modifier.offset(y = 2.dp),
                    text = LocalDateTimeUtil.getDateAsString(eyebrow.endDate, "MMM"),
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
                EyebrowText(
                    modifier = Modifier.offset(y = (-5).dp),
                    text = LocalDateTimeUtil.getDateAsString(eyebrow.endDate, "dd"),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )
            }
        }
        val daysLeft = LocalDateTimeUtil.getNumberOfDaysTillEndDate(eyebrow)
        val deadlineText = if (daysLeft > 1) {
            "$daysLeft" + " " + stringResource(R.string.home_card_eyebrow_deadline_more_than_1_day)
        } else if (daysLeft == 1) {
            "$daysLeft" + " " + stringResource(R.string.home_card_eyebrow_deadline_1_day)
        } else if (daysLeft == 0) {
            stringResource(R.string.home_card_eyebrow_deadline_today)
        } else if (daysLeft == -1) {
            abs(daysLeft).toString() + " " + stringResource(R.string.home_card_eyebrow_deadline_1_day_late)
        } else {
            abs(daysLeft).toString() + " " + stringResource(R.string.home_card_eyebrow_deadline_more_than_1_day_late)
        }
        EyebrowText(
            modifier = Modifier.padding(start = 8.dp),
            text = "$deadlineText",
            style = MaterialTheme.typography.h6
        )
    }
}