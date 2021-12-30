package com.ddairy.eyebrows.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.components.home.ParticipantDialog
import com.ddairy.eyebrows.ui.components.home.ProgressContainer
import com.ddairy.eyebrows.ui.components.home.RemoveEyebrowAlertDialog
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.LocalDateTimeUtil
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDateTime
import java.util.*

@Composable
fun EyebrowCard(
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit,
    onClickNewEyebrows: (Eyebrow) -> Unit = {},
) {
    val elevation = if (eyebrow.status == Eyebrow.Status.Open) 10.dp else 6.dp

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
                updateEyebrow = updateEyebrow,
                onClickNewEyebrows = onClickNewEyebrows
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
    Text(
        modifier = modifier,
        text = eyebrow.description,
        style = MaterialTheme.typography.h5
    )

    // Ui for the Eyebrow prize.
    if (eyebrow.prize.isNotEmpty()) {
        Row(modifier = modifier) {
            Icon(
                imageVector = Icons.Filled.EmojiEvents,
                contentDescription = "Prize"
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = eyebrow.prize)
        }
    }

    // UI for Eyebrow start and end date.
    Row(modifier = modifier) {
        // UI for start date.
        Icon(
            imageVector = Icons.Filled.Schedule,
            contentDescription = "Start Date"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = LocalDateTimeUtil.getDateAsString(eyebrow.startDate))

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
        Text(text = LocalDateTimeUtil.getDateAsString(eyebrow.endDate))
    }
}

@Composable
fun CardBottom(
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit,
    onClickNewEyebrows: (Eyebrow) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // List of participants
        var showParticipantDialog by remember { mutableStateOf(false) }
        ParticipantDialog(
            show = showParticipantDialog,
            toggleShow = { showParticipantDialog = it },
            eyebrow = eyebrow
        )

        Button(
            onClick={ showParticipantDialog = true },
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            elevation = elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            )
        ) {
            FlowRow(
                mainAxisAlignment = FlowMainAxisAlignment.Start,
                mainAxisSpacing = (-10).dp
            ) {

                for ((index, participant: Participant) in eyebrow.participants.withIndex()) {
                    if (index < 8) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = participant.name,
                            tint = participant.getIconColor()
                        )
                    }
                }
                if (eyebrow.participants.size >= 8) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Text(
                            text = "...",
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                }
            }
        }

        // Eyebrow action buttons
        FlowRow(mainAxisAlignment = FlowMainAxisAlignment.End) {
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
                    contentDescription = "More options"
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            onClickNewEyebrows(eyebrow)
                        }
                    ) {
                        Text("Edit")
                    }
                    DropdownMenuItem(
                        onClick = {
                            showRemoveAlertDialog = true
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

}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    val eyebrow = Eyebrow(
        id = UUID.randomUUID(),
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        prize = "£100",
        startDate = LocalDateTime.now().plusHours(12),
        participants = listOf(
            Participant("Bob"),
            Participant("Dan"),
            Participant("John"),
            Participant("Steve")
        )
    )
    EyebrowsTheme {
        EyebrowCard(
            eyebrow = eyebrow,
            removeEyebrow = {},
            updateEyebrow = {},
        )
    }
}

@Preview("Light Mode - No prize")
@Composable
private fun LightModeNoPrizePreview() {
    val eyebrow = Eyebrow(
        id = UUID.randomUUID(),
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        startDate = LocalDateTime.now().plusHours(12),
        participants = listOf(
            Participant("Bob"),
            Participant("Dan"),
            Participant("John"),
            Participant("Steve"),
            Participant("Brad"),
            Participant("Jack"),
            Participant("Dave"),
            Participant("Phil"),
            Participant("Bill"),
            Participant("Ted")
        )
    )
    EyebrowsTheme {
        EyebrowCard(
            eyebrow = eyebrow,
            removeEyebrow = {},
            updateEyebrow = {}
        )
    }
}