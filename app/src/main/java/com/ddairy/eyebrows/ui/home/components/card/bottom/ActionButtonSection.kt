package com.ddairy.eyebrows.ui.home.components.card.bottom

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
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
    FlowRow(mainAxisAlignment = FlowMainAxisAlignment.End) {
        MarkCompleteButton(
            eyebrow = eyebrow,
            updateEyebrow = updateEyebrow
        )
        DropDownMenu(
            eyebrow = eyebrow,
            removeEyebrow = removeEyebrow,
            onClickNewEyebrows = onClickNewEyebrows
        )
    }
}

@Composable
fun MarkCompleteButton(
    eyebrow: Eyebrow,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
){
    val context = LocalContext.current
    val isEyebrowOpen = eyebrow.status == Eyebrow.Status.Open
    val animatedColor = animateColorAsState(
        if (isEyebrowOpen) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    )
    TextButton(
        colors = if (isEyebrowOpen) ButtonDefaults.textButtonColors(contentColor = animatedColor.value) else ButtonDefaults.textButtonColors(contentColor = animatedColor.value),
        onClick = {
            eyebrow.status = if (isEyebrowOpen) Eyebrow.Status.Complete else Eyebrow.Status.Open
            updateEyebrow(context, eyebrow)
        }
    ) {
        EyebrowText(text = if (isEyebrowOpen) stringResource(R.string.home_action_open_button) else stringResource(R.string.home_action_complete_button))
    }
}

@Composable
fun DropDownMenu(
    eyebrow: Eyebrow,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    onClickNewEyebrows: (Eyebrow) -> Unit
){
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
        }
    }
}