package com.ddairy.eyebrows.ui.stake

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.components.bet.DatePicker
import com.ddairy.eyebrows.ui.components.bet.NavBarBet
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import java.time.LocalDateTime

@Composable
fun NewEyebrowsBody(
    onClickReturnHome: () -> Unit = {},
    eyebrow: Eyebrow,
    addEyebrow: (eyebrow: Eyebrow) -> Unit,
) {
    Scaffold(
        topBar = {
            NavBarBet(onClickReturnHome = onClickReturnHome)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .semantics { contentDescription = "New Eyebrow Screen" }
        ) {
            val (descriptionText, descriptionSetText) = remember { mutableStateOf(eyebrow.description) }
            val (prizeText, prizeSetText) = remember { mutableStateOf(eyebrow.prize) }
            val (startDateValue, startDateSet) = remember { mutableStateOf(eyebrow.startDate) }
            val (endDateValue, endDateSet) = remember { mutableStateOf(eyebrow.endDate) }

            val participants = remember { eyebrow.participants.toMutableStateList() }
            if (participants.isEmpty()) {
                participants.add(Participant(name = ""))
            }

            Column(modifier = Modifier.weight(1f)) {

                InputText(
                    text = descriptionText,
                    onTextChange = descriptionSetText,
                    label = "that..."
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
                        }
                    )

                    // Date Picker for end date.
                    DatePicker(
                        context = LocalContext.current,
                        date = endDateValue,
                        updateDate = { year: Int, month: Int, day: Int ->
                            endDateSet(
                                LocalDateTime.of(year, month, day, 0, 0)
                            )
                        }
                    )
                }

                Text(
                    text = "Optional Properties",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .fillMaxWidth()
                )

                // TODO: This should be fancier, should except text or money input.
                InputText(
                    text = prizeText,
                    onTextChange = prizeSetText,
                    label = "Prize"
                )

                // Participants section.
                Row(
                    verticalAlignment = Alignment.CenterVertically,
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
                        IconButton(onClick = { participants.add(Participant(name = "")) }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Participant"
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity), MaterialTheme.shapes.medium)
                ) {
                    LazyColumn {
                        itemsIndexed(items = participants) { index, participant ->
                            InputText(
                                text = participant.name,
                                onTextChange = {
                                    participants[index] = participants[index].copy(name = it)
                                },
                                label = if (participant.name.isEmpty()) "Add new participant" else "",
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (verifyInputs(
                            description = descriptionText,
                            startDate = startDateValue,
                            endDate = endDateValue)
                    ) {
                        eyebrow.description = descriptionText.trim()
                        eyebrow.prize = prizeText.trim()
                        eyebrow.startDate = startDateValue
                        eyebrow.endDate = endDateValue
                        eyebrow.participants = participants

                        // Updates participants list to only contain valid inputs.
                        // Also trims each name so there is no leading and ending spaces.
                        val participantsWithValidInputs = eyebrow.participants.filter { participant -> participant.name.isNotEmpty() }
                        participantsWithValidInputs.forEach { participant ->
                            participant.name = participant.name.trim()
                        }
                        eyebrow.participants = participantsWithValidInputs

                        // If inputs are valid then create object and return
                        addEyebrow(eyebrow)
                        onClickReturnHome()
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text("Save")
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputText(
    text: String,
    onTextChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    label: String = "",
    modifier: Modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(label) },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

private fun verifyInputs(
    description: String,
    startDate: LocalDateTime,
    endDate: LocalDateTime
): Boolean {
    // Description cannot be empty.
    if (description.trim().isEmpty()) {
        return false
    }
    // End date cannot be before the start date
    if (endDate.isBefore(startDate)) {
        return false
    }
    return true
}

@Preview("Home Body")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        NewEyebrowsBody(
            onClickReturnHome = {},
            eyebrow = Eyebrow(description = ""),
            addEyebrow = {}
        )
    }
}

@Preview("Home Body - Dark Mode")
@Composable
private fun HomePreviewDarkMode() {
    EyebrowsTheme(isLight = false) {
        NewEyebrowsBody(
            onClickReturnHome = {},
            eyebrow = Eyebrow(description = ""),
            addEyebrow = {}
        )
    }
}