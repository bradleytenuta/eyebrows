package com.ddairy.eyebrows.ui.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import androidx.core.content.ContextCompat.startActivity

/**
 * The navbar used for the home screen.
 */
@Composable
fun HomeNavBar(
    onClickNewEyebrows: () -> Unit,
    onClickViewWelcomePage: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            DropDownMenu()
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .weight(2f)
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
}

@Composable
fun DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(R.string.home_nav_bar_more)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    val uri: Uri = Uri.parse(context.resources.getString(R.string.privacy_policy_url))
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(context, intent, null)
                }
            ) {
                EyebrowText(
                    text = stringResource(R.string.home_nav_bar_dropdown_privacy_policy),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Outlined.Link,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(R.string.home_nav_bar_dropdown_privacy_policy)
                )
            }
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