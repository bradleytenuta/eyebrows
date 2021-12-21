package com.ddairy.eyebrows.ui.components.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.ui.components.NavBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@Composable
fun NavBarHome(
    onClickNewEyebrows: () -> Unit = {},
    onSwitchTheme: () -> Unit = {},
    isLightMode: Boolean
) {
    NavBar(
        title = { Text("") },
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

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    EyebrowsTheme {
        NavBarHome(
            isLightMode = true
        )
    }
}

@Preview("Dark Mode")
@Composable
private fun DarkModePreview() {
    EyebrowsTheme(
        isLight = false
    ) {
        NavBarHome(
            isLightMode = false
        )
    }
}