package com.ddairy.eyebrows.ui.home

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.model.Eyebrow
import com.ddairy.eyebrows.ui.components.EyebrowsTopBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@ExperimentalMaterial3Api
@Composable
fun HomeBody(
    onClickNewEyebrows: () -> Unit = {},
    onSwitchTheme: () -> Unit = {},
    isLightMode: Boolean,
    eyebrows: List<Eyebrow>
) {
    Scaffold(
        topBar = {
            EyebrowsTopBar(
                onClickNewEyebrows = onClickNewEyebrows,
                onSwitchTheme = onSwitchTheme,
                isLightMode = isLightMode
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .semantics { contentDescription = "Home Screen" },
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = eyebrows) { eyebrow ->
                // TODO: Make this real nice.
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row { Text(eyebrow.title) }
                        Row { Text(eyebrow.description) }
                        Row { Text(eyebrow.prize) }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview("Home Body")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        HomeBody(
            onClickNewEyebrows = {},
            isLightMode = true,
            eyebrows = listOf(
                Eyebrow(
                    title = "Title 1",
                    description = "Description 1.",
                    prize = "100"
                ),
                Eyebrow(
                    title = "Title 2",
                    description = "Description 2.",
                    prize = "A high five"
                )
            )
        )
    }
}

@ExperimentalMaterial3Api
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
                    title = "Title 1",
                    description = "Description 1.",
                    prize = "100"
                ),
                Eyebrow(
                    title = "Title 2",
                    description = "Description 2.",
                    prize = "A high five"
                )
            )
        )
    }
}