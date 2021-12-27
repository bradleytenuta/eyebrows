package com.ddairy.eyebrows.ui.stake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.bet.DatePicker
import com.ddairy.eyebrows.ui.components.bet.NavBarBet
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import java.time.LocalDateTime

@Composable
fun NewEyebrowsBody(
    onClickReturnHome: () -> Unit = {},
    eyebrow: Eyebrow = Eyebrow(description = ""),
    addEyebrow: (Eyebrow) -> Unit,
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
            }

            Button(
                onClick = {
                    // TODO: Add verify here.

                    eyebrow.description = descriptionText
                    eyebrow.prize = prizeText
                    eyebrow.startDate = startDateValue
                    eyebrow.endDate = endDateValue

                    addEyebrow(eyebrow)

                    onClickReturnHome()
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
    label: String = ""
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
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Preview("Home Body")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        NewEyebrowsBody(
            onClickReturnHome = {},
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
            addEyebrow = {}
        )
    }
}