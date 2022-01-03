package com.ddairy.eyebrows.ui.home.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDateTime
import java.util.*

/**
 * The UI for the participant pop up.
 */
@Composable
fun ParticipantDialog(
    show: Boolean,
    toggleShow: (Boolean) -> Unit,
    eyebrow: Eyebrow
) {
    if(show) {
        Dialog(onDismissRequest = { toggleShow(false) }) {
            ParticipantDialogContent(
                toggleShow = toggleShow,
                eyebrow = eyebrow
            )
        }
    }
}

@Composable
fun ParticipantDialogContent(
    toggleShow: (Boolean) -> Unit,
    eyebrow: Eyebrow
) {
    Surface(shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "All Participants",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            LazyColumn {
                items(items = eyebrow.participants) { participant ->
                    Divider()
                    Text(
                        text = participant.name,
                        modifier = Modifier.padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                    )
                }
            }

            Divider()

            FlowRow(
                mainAxisAlignment = FlowMainAxisAlignment.End,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Button(onClick = { toggleShow(false) }) {
                    Text(text = "Close")
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
        startDate = LocalDateTime.now().plusHours(12),
        participants = listOf(
            Participant("Bob"),
            Participant("Dan"),
            Participant("John"),
            Participant("Steve")
        )
    )
    EyebrowsTheme {
        ParticipantDialogContent(
            toggleShow = {},
            eyebrow = eyebrow
        )
    }
}