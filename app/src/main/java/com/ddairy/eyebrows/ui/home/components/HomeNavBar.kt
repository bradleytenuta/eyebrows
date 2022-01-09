package com.ddairy.eyebrows.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

/**
 * The navbar used for the home screen.
 */
@Composable
fun HomeNavBar(
    onClickNewEyebrows: () -> Unit,
    onClickViewWelcomePage: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        IconButton(onClick = onClickViewWelcomePage) {
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(R.string.home_nav_bar_help_icon_description)
            )
        }
        TextButton(onClick = onClickNewEyebrows) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(R.string.home_nav_bar_new_eyebrow)
            )
            EyebrowText(text = stringResource(R.string.home_nav_bar_new_eyebrow))
        }
    }
}

@Preview("Light Mode")
@Composable
private fun LightModePreview() {
    EyebrowsTheme {
        HomeNavBar(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {}
        )
    }
}