package com.ddairy.eyebrows.ui.eyebrow

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.components.EyebrowTextField
import com.ddairy.eyebrows.ui.eyebrow.components.DatePicker
import com.ddairy.eyebrows.ui.eyebrow.components.EyebrowNavBar
import com.ddairy.eyebrows.ui.eyebrow.components.SaveSection
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.helper.EyebrowUtil.Companion.isDateValid
import com.ddairy.eyebrows.util.helper.EyebrowUtil.Companion.isDescriptionValid
import org.joda.time.LocalDate
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.util.notification.EyebrowBroadcastReceiver

import android.os.Bundle
import com.ddairy.eyebrows.util.notification.NotificationConstants
import java.util.*

/**
 * The UI eyebrow screen. Used to create and edit eyebrows.
 */
@ExperimentalComposeUiApi
@Composable
fun EyebrowScreen(
    onClickReturnHome: () -> Unit,
    eyebrow: Eyebrow,
    addEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.semantics {
            contentDescription = context.resources.getString(R.string.eyebrow_description)
        },
        topBar = {
            EyebrowNavBar(onClickReturnHome = onClickReturnHome)
        }
    ) { innerPadding ->
        val (descriptionText, descriptionSetText) = remember { mutableStateOf(eyebrow.description) }
        val (descriptionIsErrorValue, descriptionIsErrorSet) = remember { mutableStateOf(false) }

        val (endDateValue, endDateSet) = remember { mutableStateOf(eyebrow.endDate) }
        val (dateIsErrorValue, dateIsErrorSet) = remember { mutableStateOf(false) }

        val participants = remember { eyebrow.participants.toMutableStateList() }
        if (participants.isEmpty()) {
            participants.add(Participant(name = ""))
        }

        // UI
        Column {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(innerPadding)
                    .semantics {
                        contentDescription =
                            context.resources.getString(R.string.eyebrow_content_description)
                    }
                    .verticalScroll(rememberScrollState())
            ) {
                // Error message is only shown when there is an error.
                if (descriptionIsErrorValue || dateIsErrorValue) {
                    val descriptionErrorMessage = stringResource(R.string.eyebrow_description_error_message)
                    val dateErrorMessage = stringResource(R.string.eyebrow_date_error_message)
                    EyebrowText(
                        text = if (descriptionIsErrorValue && dateIsErrorValue) "$descriptionErrorMessage $dateErrorMessage" else if (descriptionIsErrorValue) descriptionErrorMessage else dateErrorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }

                val keyboardController = LocalSoftwareKeyboardController.current
                EyebrowTextField(
                    value = descriptionText,
                    onValueChange = descriptionSetText,
                    label = { EyebrowText(stringResource(R.string.eyebrow_description_field_label)) },
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    isError = descriptionIsErrorValue
                )

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = Icons.Filled.Flag,
                        contentDescription = stringResource(R.string.eyebrow_end_date_icon_description)
                    )

                    EyebrowText(
                        text = stringResource(R.string.eyebrow_end_date_label),
                        style = MaterialTheme.typography.subtitle2
                    )

                    // Date Picker for end date.
                    DatePicker(
                        context = context,
                        date = endDateValue,
                        updateDate = { year: Int, month: Int, day: Int ->
                            endDateSet(
                                LocalDate(year, month, day)
                            )
                        },
                        borderColor = if (dateIsErrorValue) MaterialTheme.colors.error else Color.Gray
                    )
                }

                EyebrowText(
                    text = stringResource(R.string.eyebrow_optional_properties_header),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .fillMaxWidth()
                )

                // Participants section.
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    EyebrowText(
                        text = stringResource(R.string.eyebrow_participants_header),
                        style = MaterialTheme.typography.subtitle2
                    )
                    if (participants.size < 10) {
                        TextButton(
                            onClick = { participants.add(Participant(name = "")) },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.secondary)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(R.string.eyebrow_add_participant)
                            )
                            EyebrowText(text = stringResource(R.string.eyebrow_add_participant))
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
                            MaterialTheme.shapes.medium
                        )
                ) {
                    participants.forEachIndexed { index, participant ->
                        val keyboardController = LocalSoftwareKeyboardController.current
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                .fillMaxWidth()
                        ) {
                            EyebrowTextField(
                                value = participant.name,
                                onValueChange = {
                                    participants[index] = participants[index].copy(name = it)
                                },
                                label = { EyebrowText(stringResource(R.string.eyebrow_participant_label)) },
                                maxLines = 1,
                                singleLine = true,
                                keyboardActions = KeyboardActions(onDone = {
                                    keyboardController?.hide()
                                }),
                                modifier = Modifier
                                    .padding(0.dp)
                                    .weight(1f)
                            )
                            IconButton(
                                onClick = { participants.removeAt(index) },
                                modifier = Modifier.padding(top = 8.dp),
                                enabled = participant.name.isNotEmpty()
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = stringResource(R.string.eyebrow_delete_participant_icon_description)
                                )
                            }
                        }
                    }
                }
            }

            // Logic for the save button section.
            SaveSection(
                onSave = {
                    if (isEyebrowValid(descriptionText, endDateValue, eyebrow.status)) {
                        eyebrow.description = descriptionText.trim()
                        eyebrow.endDate = endDateValue

                        // Updates participants list to only contain valid inputs.
                        // Also trims each name so there is no leading and ending spaces.
                        val participantsWithValidInputs =
                            participants.filter { participant -> participant.name.isNotEmpty() }
                        participantsWithValidInputs.forEach { participant ->
                            participant.name = participant.name.trim()
                        }
                        eyebrow.participants = participantsWithValidInputs

                        // If inputs are valid then create object and return.
                        addEyebrow(context, eyebrow)
                        addEyebrowNotification(context, eyebrow)
                        onClickReturnHome()
                    } else {
                        descriptionIsErrorSet(!isDescriptionValid(description = descriptionText))
                        dateIsErrorSet(!isDateValid(startDate = LocalDate.now(), endDate = endDateValue))
                    }
                }
            )

        }
    }
}

/**
 * Returns true if the description is valid and
 * the date is valid or the eyebrow is complete.
 */
private fun isEyebrowValid(
    descriptionText: String,
    endDateValue: LocalDate,
    status: Eyebrow.Status
): Boolean {
    return isDescriptionValid(description = descriptionText) && (isDateValid(startDate = LocalDate.now(), endDate = endDateValue) || status == Eyebrow.Status.Complete)
}

/**
 * Adds a notification to go off when the deadline for this eyebrow is reached.
 */
private fun addEyebrowNotification(context: Context, eyebrow: Eyebrow) {
    val intent = Intent(context, EyebrowBroadcastReceiver::class.java)

    // Adds values about the eyebrow to the intent.
    // TODO: values don't update maybe notification channel needs deleting?
    val bundle = Bundle()
    bundle.putString(NotificationConstants.eyebrowDescriptionKey, eyebrow.description);
    bundle.putString(NotificationConstants.eyebrowIdKey, eyebrow.id.toString());
    intent.putExtras(bundle)

    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    // Sets the exact time the notification should be sent.
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, eyebrow.endDate.year) // Year value stays the same.
    calendar.set(Calendar.MONTH, eyebrow.endDate.monthOfYear - 1) // First month is 0 (Jan)
    calendar.set(Calendar.DAY_OF_MONTH, eyebrow.endDate.dayOfMonth) // First day of month is 1. // TODO: plus 1 to this day so notification comes after deadline reach not at beginning of deadline day.

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}

@ExperimentalComposeUiApi
@Preview("Eyebrow Screen")
@Composable
private fun EyebrowPreview() {
    EyebrowsTheme {
        EyebrowScreen(
            onClickReturnHome = {},
            eyebrow = Eyebrow(description = ""),
            addEyebrow = { _: Context, _: Eyebrow -> }
        )
    }
}

@ExperimentalComposeUiApi
@Preview("Eyebrow Screen - Dark Mode")
@Composable
private fun EyebrowPreviewDarkMode() {
    val eyebrow = Eyebrow(
        description = "description here",
        endDate = LocalDate(2020, 1, 2),
        participants = listOf(Participant(name = "Bob"), Participant(name = "Jim"))
    )

    EyebrowsTheme(darkTheme = true) {
        EyebrowScreen(
            onClickReturnHome = {},
            eyebrow = eyebrow,
            addEyebrow = { _: Context, _: Eyebrow -> }
        )
    }
}