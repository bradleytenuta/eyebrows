package com.ddairy.eyebrows.ui.stake

import android.util.Log
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
import com.ddairy.eyebrows.model.Eyebrow
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@ExperimentalMaterial3Api
@Composable
fun NewEyebrowsBody(
    onClickReturnHome: () -> Unit = {},
    eyebrow: Eyebrow = Eyebrow(),
    addEyebrow: (Eyebrow) -> Unit,
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("New Eyebrow") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = onClickReturnHome) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Return Home"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .semantics { contentDescription = "New Eyebrow Screen" }
        ) {
            val (titleText, titleSetText) = remember { mutableStateOf(eyebrow.title) }
            val (descriptionText, descriptionSetText) = remember { mutableStateOf(eyebrow.description) }
            val (prizeText, prizeSetText) = remember { mutableStateOf(eyebrow.prize) }

            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    InputText(
                        text = titleText,
                        onTextChange = titleSetText,
                        maxLines = 1,
                        label = "Title"
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    InputText(
                        text = descriptionText,
                        onTextChange = descriptionSetText,
                        label = "Description"
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

                    eyebrow.title = titleText
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

@ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
@Preview("Home Body - Dark Mode")
@Composable
private fun HomePreviewDarkMode() {
    EyebrowsTheme(
        isLight = false
    ) {
        NewEyebrowsBody(
            onClickReturnHome = {},
            addEyebrow = {}
        )
    }
}