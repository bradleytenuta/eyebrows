package com.ddairy.eyebrows.ui.home.components

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.ui.components.NavBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

/**
 * The navbar used for the home screen.
 */
@Composable
fun HomeNavBar(
    onClickNewEyebrows: () -> Unit = {}
) {
    NavBar(
        title = { Text("") },
        actions = {
            TextButton(onClick = onClickNewEyebrows) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "New Eyebrow"
                )
                Text(text = "New Eyebrow")
            }
        }
    )
}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    EyebrowsTheme {
        HomeNavBar()
    }
}