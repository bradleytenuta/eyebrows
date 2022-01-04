package com.ddairy.eyebrows.ui.eyebrow

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.components.EyebrowTextField
import com.ddairy.eyebrows.ui.eyebrow.components.DatePicker
import com.ddairy.eyebrows.ui.eyebrow.components.EyebrowNavBar
import com.ddairy.eyebrows.ui.eyebrow.components.SaveSection
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import java.time.LocalDateTime
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.ddairy.eyebrows.util.helper.EyebrowUtil.Companion.isDatesValid
import com.ddairy.eyebrows.util.helper.EyebrowUtil.Companion.isDescriptionValid

// TODO: Refactor this file.
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
    Scaffold(
        topBar = {
            EyebrowNavBar(onClickReturnHome = onClickReturnHome)
        }
    ) { innerPadding ->
        val (descriptionText, descriptionSetText) = remember { mutableStateOf(eyebrow.description) }
        val (descriptionIsErrorValue, descriptionIsErrorSet) = remember { mutableStateOf(false) }

        val (startDateValue, startDateSet) = remember { mutableStateOf(eyebrow.startDate) }
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
                    .semantics { contentDescription = "New Eyebrow Screen" }
                    .verticalScroll(rememberScrollState())
            ) {
                // Error message is only shown when there is an error.
                if (descriptionIsErrorValue || dateIsErrorValue) {
                    val descriptionErrorMessage = "A description must be provided."
                    val dateErrorMessage = "The start date must occur before the end date."
                    Text(
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
                    label = { Text("that...") },
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    isError = descriptionIsErrorValue
                )

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Date Picker for start date.
                    DatePicker(
                        context = LocalContext.current,
                        date = startDateValue,
                        updateDate = { year: Int, month: Int, day: Int ->
                            startDateSet(
                                LocalDateTime.of(year, month, day, 0, 0)
                            )
                        },
                        borderColor = if (dateIsErrorValue) MaterialTheme.colors.error else Color.Gray
                    )

                    // Date Picker for end date.
                    DatePicker(
                        context = LocalContext.current,
                        date = endDateValue,
                        updateDate = { year: Int, month: Int, day: Int ->
                            endDateSet(
                                LocalDateTime.of(year, month, day, 0, 0)
                            )
                        },
                        borderColor = if (dateIsErrorValue) MaterialTheme.colors.error else Color.Gray
                    )
                }

                Text(
                    text = "Optional",
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
                    Text(
                        text = "Participants",
                        style = MaterialTheme.typography.subtitle2
                    )
                    if (participants.size < 10) {
                        TextButton(onClick = { participants.add(Participant(name = "")) }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Participant"
                            )
                            Text(text = "Add Participant")
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
                                label = { Text("Name...") },
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
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }
                }
            }

            // Logic for the save button section.
            val context = LocalContext.current
            SaveSection(
                onSave = {
                   if (isDescriptionValid(description = descriptionText) && isDatesValid(startDate = startDateValue, endDate = endDateValue)) {
                        eyebrow.description = descriptionText.trim()
                        eyebrow.startDate = startDateValue
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
                        onClickReturnHome()
                    } else {
                        descriptionIsErrorSet(!isDescriptionValid(description = descriptionText))
                        dateIsErrorSet(!isDatesValid(startDate = startDateValue, endDate = endDateValue))
                    }
                }
            )

        }
    }
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
        startDate = LocalDateTime.of(2020, 1, 1, 12, 0),
        endDate = LocalDateTime.of(2020, 1, 2, 12, 0),
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