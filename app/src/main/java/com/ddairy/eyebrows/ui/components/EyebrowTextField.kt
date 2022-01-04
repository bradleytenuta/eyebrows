package com.ddairy.eyebrows.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

/**
 * The default text field used by the eyebrows application.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EyebrowTextField(
    modifier: Modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(),
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        isError = isError
    )
}