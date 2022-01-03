package com.ddairy.eyebrows.ui.components.home

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ddairy.eyebrows.data.Eyebrow

@Composable
fun RemoveEyebrowAlertDialog(
    show: Boolean,
    toggleShow: (Boolean) -> Unit,
    eyebrow: Eyebrow,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit
) {
    if(show) {
        val context = LocalContext.current
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
                        removeEyebrow(context, eyebrow)
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
}