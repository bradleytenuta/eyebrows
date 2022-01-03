package com.ddairy.eyebrows.ui.home.components.card

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Participant
import com.ddairy.eyebrows.ui.home.components.card.bottom.CardBottom
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import java.time.LocalDateTime
import java.util.*

/**
 * The main UI for the eyebrow card.
 */
@Composable
fun EyebrowCard(
    eyebrow: Eyebrow,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
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
        EyebrowCard(
            eyebrow = eyebrow,
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> }
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
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> }
        )
    }
}