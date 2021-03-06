package com.ddairy.eyebrows.ui.eyebrow.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.helper.LocalDateUtil
import org.joda.time.LocalDate

/**
 * UI that allows the user to select a given date.
 */
@Composable
fun DatePicker(
    context: Context,
    date: LocalDate,
    borderColor: Color = Color.Gray,
    updateDate: (year: Int, month: Int, day: Int) -> Unit
) {
    // Month value needs minus 1 and plus 1 as the value is used instead of int value.
    // e.g. december is 12 in value but should be 11 in picker due to starting at 0.
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            updateDate(year, month + 1, dayOfMonth)
        },
        date.year,
        date.monthOfYear - 1,
        date.dayOfMonth
    )

    TextButton(
        modifier = Modifier.padding(horizontal = 16.dp),
        border = BorderStroke(1.dp, borderColor),
        onClick = {
            datePickerDialog.show()
        }
    ) {
        LocalDate.now()
        val text =
            when {
                date.isEqual(LocalDate.now()) -> stringResource(R.string.home_card_eyebrow_datepicker_today)
                date.isEqual(
                    LocalDate.now().minusDays(1)
                ) -> stringResource(R.string.home_card_eyebrow_datepicker_yesterday)
                date.isEqual(
                    LocalDate.now().plusDays(1)
                ) -> stringResource(R.string.home_card_eyebrow_datepicker_tomorrow)
                else -> LocalDateUtil.getDateAsString(
                    date,
                    "dd/MM/yyyy"
                )
            }
        EyebrowText(
            text = text,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    EyebrowsTheme {
        DatePicker(
            context = LocalContext.current,
            date = LocalDate.now(),
            updateDate = { _: Int, _: Int, _: Int -> }
        )
    }
}

@Preview("Light Mode - Error")
@Composable
private fun LightModeErrorPreview() {
    EyebrowsTheme {
        DatePicker(
            context = LocalContext.current,
            date = LocalDate.now(),
            updateDate = { _: Int, _: Int, _: Int -> },
            borderColor = MaterialTheme.colors.error
        )
    }
}

@Preview("Light Mode - 1 day ahead")
@Composable
private fun LightModeOneDayAheadPreview() {
    EyebrowsTheme {
        DatePicker(
            context = LocalContext.current,
            date = LocalDate.now().plusDays(1),
            updateDate = { _: Int, _: Int, _: Int -> }
        )
    }
}

@Preview("Light Mode - 1 day behind")
@Composable
private fun LightModeOneDayBehindPreview() {
    EyebrowsTheme {
        DatePicker(
            context = LocalContext.current,
            date = LocalDate.now().minusDays(1),
            updateDate = { _: Int, _: Int, _: Int -> }
        )
    }
}

@Preview("Light Mode - another date ahead")
@Composable
private fun LightModeAnotherDateAheadPreview() {
    EyebrowsTheme {
        DatePicker(
            context = LocalContext.current,
            date = LocalDate.now().plusDays(5),
            updateDate = { _: Int, _: Int, _: Int -> }
        )
    }
}

@Preview("Light Mode - another date behind")
@Composable
private fun LightModeAnotherDateBehindPreview() {
    EyebrowsTheme {
        DatePicker(
            context = LocalContext.current,
            date = LocalDate.now().minusDays(5),
            updateDate = { _: Int, _: Int, _: Int -> }
        )
    }
}