package com.ddairy.eyebrows.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.home.ProgressContainer
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDateTime
import java.util.*

@Composable
fun EyebrowCard(
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit
) {
    val elevation = if (eyebrow.status == Eyebrow.Status.Open) 10.dp else 1.dp

    Card(
        shape = MaterialTheme.shapes.large,
        elevation = elevation,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            CardContent(eyebrow = eyebrow)
            Divider()
            CardBottom(
                eyebrow = eyebrow,
                removeEyebrow = removeEyebrow,
                updateEyebrow = updateEyebrow
            )
        }
    }
}

@Composable
fun CardContent(eyebrow: Eyebrow) {
    // Custom Modifier for the UI elements above the main divider.
    val modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    // UI for Eyebrow description.
    Row(modifier = modifier) {
        Text(
            text = eyebrow.description,
            style = MaterialTheme.typography.h5
        )
    }

    // Ui for the Eyebrow prize.
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.EmojiEvents,
            contentDescription = "Prize"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = eyebrow.prize)
    }

    // UI for Eyebrow start and end date.
    Row(modifier = modifier) {
        // UI for start date.
        Icon(
            imageVector = Icons.Filled.Schedule,
            contentDescription = "Start Date"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = eyebrow.getStartDateAsString())

        // Progress bar to end date.
        ProgressContainer(
            eyebrow = eyebrow,
            Modifier.weight(1f)
        )

        // UI for end date.
        Icon(
            imageVector = Icons.Filled.Flag,
            contentDescription = "End Date"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = eyebrow.getEndDateAsString())
    }
}

@Composable
fun CardBottom(
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisAlignment = FlowMainAxisAlignment.End
    ) {
        if (eyebrow.status == Eyebrow.Status.Open) {
            IconButton(
                onClick = {
                    eyebrow.status = Eyebrow.Status.Complete
                    updateEyebrow(eyebrow)
                }
            ) {
                Icon(
                    Icons.Outlined.Done,
                    contentDescription = "Mark as complete"
                )
            }
        }

        var expanded by remember { mutableStateOf(false) }
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Outlined.MoreVert,
                contentDescription = "More options"
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        // TODO: Find a way to pass an eyebrow object through the navigation.
                        //onClickNewEyebrows(eyebrow)
                    }
                ) {
                    Text("Edit")
                }
                DropdownMenuItem(
                    onClick = {
                        // TODO: add an 'are you sure?'
                        removeEyebrow(eyebrow)
                    }
                ) {
                    Text("Remove")
                }
                if (eyebrow.status == Eyebrow.Status.Complete) {
                    DropdownMenuItem(
                        onClick = {
                            eyebrow.status = Eyebrow.Status.Open
                            updateEyebrow(eyebrow)
                        }
                    ) {
                        Text("Mark as not complete")
                    }
                }
            }
        }
    }
}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    val eyebrow = Eyebrow(
        id = UUID.randomUUID(),
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        prize = "£100",
        startDate = LocalDateTime.now().plusHours(12)
    )
    EyebrowsTheme {
        EyebrowCard(
            eyebrow = eyebrow,
            removeEyebrow = {},
            updateEyebrow = {}
        )
    }
}