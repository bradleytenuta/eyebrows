package com.ddairy.eyebrows.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@Composable
fun EyebrowsTopBar(
    onClickNewEyebrows: () -> Unit = {},
    onSwitchTheme: () -> Unit = {},
    isLightMode: Boolean
) {
    SmallTopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        actions = {
            IconButton(onClick = onSwitchTheme) {
                if (isLightMode) {
                    Icon(
                        imageVector = Icons.Filled.DarkMode,
                        contentDescription = "Turn on Dark Mode"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.LightMode,
                        contentDescription = "Turn on Light Mode"
                    )
                }
            }
            IconButton(onClick = onClickNewEyebrows) {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = "Create Eyebrows"
                )
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview("Eyebrows Top Bar")
@Composable
private fun EyebrowsTopBarPreview() {
    EyebrowsTheme {
        EyebrowsTopBar(
            isLightMode = true
        )
    }
}

@ExperimentalMaterial3Api
@Preview("Eyebrows Top Bar - Dark Mode")
@Composable
private fun EyebrowsTopBarDarkMode() {
    EyebrowsTheme(
        isLight = false
    ) {
        EyebrowsTopBar(
            isLightMode = false
        )
    }
}