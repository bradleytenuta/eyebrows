package com.ddairy.eyebrows.ui.eyebrow.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.components.NavBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

/**
 * The navbar used for the eyebrow screen.
 */
@Composable
fun EyebrowNavBar(
    onClickReturnHome: () -> Unit
) {
    NavBar(
        title = {
            EyebrowText(
                text = stringResource(R.string.eyebrow_nav_bar_title),
                style = MaterialTheme.typography.h6
            )
        },
        actions = {
            TextButton(onClick = onClickReturnHome) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(R.string.eyebrow_nav_bar_cancel)
                )
                EyebrowText(text = stringResource(R.string.eyebrow_nav_bar_cancel))
            }
        }
    )
}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    EyebrowsTheme {
        EyebrowNavBar(
            onClickReturnHome = {}
        )
    }
}