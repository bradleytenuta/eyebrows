package com.ddairy.eyebrows.ui.eyebrow.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.ui.components.NavBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

/**
 * The navbar used for the eyebrow screen.
 */
@Composable
fun EyebrowNavBar(
    onClickReturnHome: () -> Unit = {}
) {
    NavBar(
        title = { Text("I bet Eyebrows...") },
        actions = {
            IconButton(onClick = onClickReturnHome) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Return Home"
                )
            }
        }
    )
}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    EyebrowsTheme {
        EyebrowNavBar()
    }
}