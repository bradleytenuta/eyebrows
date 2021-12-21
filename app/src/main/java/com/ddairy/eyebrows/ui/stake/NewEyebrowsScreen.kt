package com.ddairy.eyebrows.ui.stake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.bet.NavBarBet
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@Composable
fun NewEyebrowsBody(
    onClickReturnHome: () -> Unit = {},
    eyebrow: Eyebrow = Eyebrow(),
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

            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    InputText(
                        text = descriptionText,
                        onTextChange = descriptionSetText,
                        label = "that..."
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    InputText(
                        text = prizeText,
                        onTextChange = prizeSetText,
                        label = "Prize"
                    )
                }
                // TODO: Add date support.
            }

            Button(
                onClick = {
                    // TODO: Add verify here.

                    eyebrow.description = descriptionText
                    eyebrow.prize = prizeText
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