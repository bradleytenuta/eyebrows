package com.ddairy.eyebrows.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun HomeBody(
    onClickNewEyebrows: () -> Unit = {},
    onSwitchTheme: () -> Unit = {},
    isLightMode: Boolean,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit
) {
    Scaffold(
        topBar = {
            NavBarHome(
                onClickNewEyebrows = onClickNewEyebrows,
                onSwitchTheme = onSwitchTheme,
                isLightMode = isLightMode
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .semantics { contentDescription = "Home Screen" },
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = organiseList(eyebrows, Eyebrow.Status.Open)) { eyebrow ->
                EyebrowListItem(
                    eyebrow = eyebrow,
                    removeEyebrow = removeEyebrow,
                    updateEyebrow = updateEyebrow
                )
            }

            // TODO: Add some type of divider here with padding. Only if complete list contains more than one.

            items(items = organiseList(eyebrows, Eyebrow.Status.Complete)) { eyebrow ->
                EyebrowListItem(
                    eyebrow = eyebrow,
                    removeEyebrow = removeEyebrow,
                    updateEyebrow = updateEyebrow
                )
            }
        }
    }
}

@Composable
fun EyebrowListItem(
    eyebrow: Eyebrow,
    removeEyebrow: (Eyebrow) -> Unit,
    updateEyebrow: (Eyebrow) -> Unit
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
            updateEyebrow = updateEyebrow
        )
    }
}

private fun organiseList(
    eyebrows: List<Eyebrow>,
    status: Eyebrow.Status
): List<Eyebrow> {
    var newList = eyebrows.filter { it.status == status }
    newList.sortedByDescending { it.endDate } // TODO: This does not work as can see in the preview.
    return newList
}

@Preview("Home Body")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        HomeBody(
            onClickNewEyebrows = {},
            isLightMode = true,
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    prize = "A high five",
                    endDate = LocalDateTime.now(),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    prize = "100",
                    endDate = LocalDateTime.now().plusDays(3)
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    prize = "100",
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
    EyebrowsTheme(
        isLight = false
    ) {
        HomeBody(
            onClickNewEyebrows = {},
            isLightMode = false,
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    prize = "A high five",
                    endDate = LocalDateTime.now(),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    prize = "100",
                    endDate = LocalDateTime.now().plusDays(3)
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    prize = "100",
                    endDate = LocalDateTime.now().plusDays(1)
                )
            ),
            removeEyebrow = {},
            updateEyebrow = {}
        )
    }
}