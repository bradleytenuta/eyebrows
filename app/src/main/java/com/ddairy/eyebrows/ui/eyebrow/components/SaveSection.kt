package com.ddairy.eyebrows.ui.eyebrow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * The UI for the section at the bottom of the eyebrow screen.
 * This contains the saving of the eyebrow.
 */
@Composable
fun SaveSection(onSave: () -> Unit) {
    // Used to separate from the input form above.
    Divider()

    Button(
        onClick = { onSave() },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text("Save")
    }
}