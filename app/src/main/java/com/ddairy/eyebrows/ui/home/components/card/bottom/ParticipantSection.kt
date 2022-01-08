package com.ddairy.eyebrows.ui.home.components.card.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.home.components.card.ParticipantDialog
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

/**
 * The UI for the participant part of the eyebrow card.
 */
@Composable
fun ParticipantSection(eyebrow: Eyebrow) {
    var showParticipantDialog by remember { mutableStateOf(false) }
    ParticipantDialog(
        show = showParticipantDialog,
        toggleShow = { showParticipantDialog = it },
        eyebrow = eyebrow
    )

    Button(
        onClick={ showParticipantDialog = true },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        enabled = eyebrow.participants.isNotEmpty()
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
                    EyebrowText(
                        text = "...",
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}