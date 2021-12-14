package com.ddairy.eyebrows.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.ui.components.EyebrowsTopBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@ExperimentalMaterial3Api
@Composable
fun HomeBody(
    onClickNewEyebrows: () -> Unit = {},
    onSwitchTheme: () -> Unit = {},
    isLightMode: Boolean
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .semantics { contentDescription = "Home Screen" }
        ) {

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
            isLightMode = true
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
            isLightMode = false
        )
    }
}