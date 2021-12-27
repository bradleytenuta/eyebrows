package com.ddairy.eyebrows.ui.components.bet

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.util.LocalDateTimeUtil
import java.time.LocalDateTime

@Composable
fun DatePicker(
    context: Context,
    date: LocalDateTime,
    updateDate: (year: Int, month: Int, day: Int) -> Unit
){
    // Month value needs minus 1 and plus 1 as the value is used instead of int value.
    // e.g. december is 12 in value but should be 11 in picker due to starting at 0.
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            updateDate(year, month + 1, dayOfMonth)
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth
    )

    TextButton(
        modifier = Modifier.padding(horizontal = 16.dp),
        border = BorderStroke(1.dp, Color.Gray),
        onClick = {
            datePickerDialog.show()
        }
    ) {
        Text(text = LocalDateTimeUtil.getDateAsString(date))
    }
}