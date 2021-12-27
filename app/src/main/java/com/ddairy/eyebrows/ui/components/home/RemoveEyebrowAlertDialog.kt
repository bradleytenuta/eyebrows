package com.ddairy.eyebrows.ui.components.home

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.ddairy.eyebrows.data.Eyebrow

@Composable
fun RemoveEyebrowAlertDialog(
    show: Boolean,
    toggleShow: (Boolean) -> Unit,
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit
) {
    if(show)
        AlertDialog(
            onDismissRequest = {
                toggleShow(false)
            },
            title = {
                Text(text = "Delete Eyebrow")
            },
            text = {
                Text("Are you sure?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        toggleShow(false)
                        removeEyebrow(eyebrow)
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        toggleShow(false)
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
}