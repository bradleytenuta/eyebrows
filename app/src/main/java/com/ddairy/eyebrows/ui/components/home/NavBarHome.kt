package com.ddairy.eyebrows.ui.components.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.ui.components.NavBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

@Composable
fun NavBarHome(
    onClickNewEyebrows: () -> Unit = {}
) {
    NavBar(
        title = { Text("") },
        actions = {
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
        NavBarHome()
    }
}