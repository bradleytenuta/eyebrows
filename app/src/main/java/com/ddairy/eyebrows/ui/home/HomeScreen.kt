package com.ddairy.eyebrows.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.components.EyebrowCard
import com.ddairy.eyebrows.ui.components.home.NavBarHome
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.EyebrowUtil
import java.time.LocalDateTime

@Composable
fun HomeBody(
    onClickNewEyebrows: (Eyebrow) -> Unit,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit
) {
    Scaffold(
        topBar = {
            NavBarHome(
                onClickNewEyebrows = { onClickNewEyebrows(Eyebrow(description = "")) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .semantics { contentDescription = "Home Screen" },
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            val organisedList = EyebrowUtil.organiseList(eyebrows)
            itemsIndexed(items = organisedList) { index, eyebrow ->
                EyebrowListItem(
                    eyebrow = eyebrow,
                    removeEyebrow = removeEyebrow,
                    updateEyebrow = updateEyebrow,
                    onClickNewEyebrows = onClickNewEyebrows
                )

                // Creates a divider between the open and completed eyebrows.
                val newIndex = index + 1
                if (
                    newIndex < organisedList.size &&
                    eyebrow.status == Eyebrow.Status.Open &&
                    organisedList[newIndex].status == Eyebrow.Status.Complete
                ) {
                    Divider(
                        modifier = Modifier.padding(vertical = 20.dp),
                        thickness = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun EyebrowListItem(
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit,
    onClickNewEyebrows: (Eyebrow) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        EyebrowCard(
            eyebrow = eyebrow,
            removeEyebrow = removeEyebrow,
            updateEyebrow = updateEyebrow,
            onClickNewEyebrows = onClickNewEyebrows
        )
    }
}

@Preview("Home Body")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        HomeBody(
            onClickNewEyebrows = {},
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now(),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now().plusDays(3)
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now().plusDays(1)
                )
            ),
            removeEyebrow = {},
            updateEyebrow = {}
        )
    }
}

@Preview("Home Body - Dark Mode")
@Composable
private fun HomePreviewDarkMode() {
    EyebrowsTheme {
        HomeBody(
            onClickNewEyebrows = {},
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    startDate = LocalDateTime.now().minusDays(1),
                    endDate = LocalDateTime.now().plusDays(1),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    startDate = LocalDateTime.now().minusYears(30),
                    endDate = LocalDateTime.now().minusDays(1),
                )
            ),
            removeEyebrow = {},
            updateEyebrow = {}
        )
    }
}