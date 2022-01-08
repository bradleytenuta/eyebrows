package com.ddairy.eyebrows.ui.home.components.card.bottom

import android.content.Context
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.home.components.card.RemoveEyebrowAlertDialog
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

/**
 * The UI for the action button section of the eyebrow card.
 */
@Composable
fun ActionButtonSection(
    eyebrow: Eyebrow,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    onClickNewEyebrows: (Eyebrow) -> Unit
) {
    val context = LocalContext.current
    FlowRow(mainAxisAlignment = FlowMainAxisAlignment.End) {
        if (eyebrow.status == Eyebrow.Status.Open) {
            IconButton(
                onClick = {
                    eyebrow.status = Eyebrow.Status.Complete
                    updateEyebrow(context, eyebrow)
                }
            ) {
                Icon(
                    Icons.Outlined.Done,
                    contentDescription = stringResource(R.string.home_action_complete_icon_description)
                )
            }
        }

        var showRemoveAlertDialog by remember { mutableStateOf(false) }
        RemoveEyebrowAlertDialog(
            show = showRemoveAlertDialog,
            toggleShow = { showRemoveAlertDialog = it },
            eyebrow = eyebrow,
            removeEyebrow = removeEyebrow
        )

        var expanded by remember { mutableStateOf(false) }
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Outlined.MoreVert,
                contentDescription = stringResource(R.string.home_action_more_options_icon_description)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onClickNewEyebrows(eyebrow)
                    }
                ) {
                    EyebrowText(stringResource(R.string.home_action_dropdown_edit))
                }
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showRemoveAlertDialog = true
                    }
                ) {
                    EyebrowText(stringResource(R.string.home_action_dropdown_remove))
                }
                if (eyebrow.status == Eyebrow.Status.Complete) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            eyebrow.status = Eyebrow.Status.Open
                            updateEyebrow(context, eyebrow)
                        }
                    ) {
                        EyebrowText(stringResource(R.string.home_action_dropdown_mark_as_not_complete))
                    }
                }
            }
        }
    }
}