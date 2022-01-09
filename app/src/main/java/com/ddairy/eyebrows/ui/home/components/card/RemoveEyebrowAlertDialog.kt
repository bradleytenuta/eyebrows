package com.ddairy.eyebrows.ui.home.components.card

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.EyebrowText

/**
 * The UI for the eyebrow delete pop up.
 */
@Composable
fun RemoveEyebrowAlertDialog(
    show: Boolean,
    toggleShow: (Boolean) -> Unit,
    eyebrow: Eyebrow,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit
) {
    if (show) {
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = {
                toggleShow(false)
            },
            title = {
                EyebrowText(text = stringResource(R.string.home_card_remove_eyebrow_dialog_title))
            },
            text = {
                EyebrowText(text = stringResource(R.string.home_card_remove_eyebrow_dialog_text))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        toggleShow(false)
                        removeEyebrow(context, eyebrow)
                    }
                ) {
                    EyebrowText(text = stringResource(R.string.home_card_remove_eyebrow_dialog_delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        toggleShow(false)
                    }
                ) {
                    EyebrowText(text = stringResource(R.string.home_card_remove_eyebrow_dialog_cancel))
                }
            }
        )
    }
}