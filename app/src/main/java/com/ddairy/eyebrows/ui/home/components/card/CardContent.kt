package com.ddairy.eyebrows.ui.home.components.card

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.util.helper.LocalDateUtil
import org.joda.time.LocalDate
import kotlin.math.abs
import kotlin.time.ExperimentalTime

/**
 * The UI for the main content of the eyebrow card.
 */
@ExperimentalTime
@Composable
fun CardContent(eyebrow: Eyebrow) {
    // Subheading
    EyebrowText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 0.dp
            ),
        text = stringResource(R.string.home_card_sub_title),
        style = MaterialTheme.typography.subtitle2,
        color = MaterialTheme.colors.secondary
    )
    // UI for Eyebrow description.
    EyebrowText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 0.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            ),
        text = eyebrow.description,
        style = MaterialTheme.typography.h5
    )
    // UI for Eyebrow start and end date.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                    text = LocalDateUtil.getDateAsString(eyebrow.endDate, "MMM"),
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
                EyebrowText(
                    modifier = Modifier.offset(y = (-5).dp),
                    text = LocalDateUtil.getDateAsString(eyebrow.endDate, "dd"),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White
                )
            }
        }
        val deadlineText = createDeadlineText(LocalContext.current, eyebrow)
        EyebrowText(
            modifier = Modifier.padding(start = 8.dp),
            text = "$deadlineText",
            style = MaterialTheme.typography.h6
        )
    }
}

@ExperimentalTime
private fun createDeadlineText(context: Context, eyebrow: Eyebrow): String {
    val daysLeft = LocalDateUtil.getNumberOfDaysTillEndDate(eyebrow, LocalDate.now())
    if (Eyebrow.Status.Complete == eyebrow.status) {
        return context.resources.getString(R.string.home_card_eyebrow_deadline_completed)
    }
    return when {
        daysLeft > 1 -> {
            "$daysLeft" + " " + context.resources.getString(R.string.home_card_eyebrow_deadline_more_than_1_day)
        }
        daysLeft == 1 -> {
            "$daysLeft" + " " + context.resources.getString(R.string.home_card_eyebrow_deadline_1_day)
        }
        daysLeft == 0 -> {
            context.resources.getString(R.string.home_card_eyebrow_deadline_today)
        }
        daysLeft == -1 -> {
            abs(daysLeft).toString() + " " + context.resources.getString(R.string.home_card_eyebrow_deadline_1_day_late)
        }
        else -> {
            abs(daysLeft).toString() + " " + context.resources.getString(R.string.home_card_eyebrow_deadline_more_than_1_day_late)
        }
    }
}