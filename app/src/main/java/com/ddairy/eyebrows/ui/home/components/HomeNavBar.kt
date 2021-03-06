package com.ddairy.eyebrows.ui.home.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
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
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import androidx.core.content.ContextCompat.startActivity
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.util.helper.GeneralUtil
import com.ddairy.eyebrows.util.tag.FlagTag
import com.ddairy.eyebrows.util.tag.LocaleCode
import com.murgupluoglu.flagkit.FlagKit

/**
 * The navbar used for the home screen.
 */
@Composable
fun HomeNavBar(
    onClickNewEyebrows: () -> Unit,
    onClickViewWelcomePage: () -> Unit,
    preferences: Preferences,
    onClickUpdatePreferences: (context: Context, preferences: Preferences) -> Unit,
    refreshHomePage: () -> Unit
) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            DropDownMenu(
                preferences = preferences,
                onClickUpdatePreferences = onClickUpdatePreferences,
                refreshHomePage = refreshHomePage
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .weight(2f)
                .padding(8.dp)
        ) {
            IconButton(
                onClick = onClickViewWelcomePage,
                modifier = Modifier.semantics { contentDescription = context.resources.getString(R.string.home_nav_bar_help_icon_description) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.HelpOutline,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(R.string.home_nav_bar_help_icon_description)
                )
            }
            TextButton(
                onClick = onClickNewEyebrows,
                modifier = Modifier.semantics { contentDescription = context.resources.getString(R.string.home_nav_bar_new_eyebrow) }
            ) {
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
fun DropDownMenu(
    preferences: Preferences,
    onClickUpdatePreferences: (context: Context, preferences: Preferences) -> Unit,
    refreshHomePage: () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Outlined.MoreHoriz,
            tint = MaterialTheme.colors.primary,
            contentDescription = stringResource(R.string.home_nav_bar_more)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                modifier = Modifier.padding(bottom = 8.dp),
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
            DropdownMenuItem(
                modifier = Modifier.padding(bottom = 8.dp),
                onClick = {
                    expanded = false
                    var newPreferences = Preferences(
                        showWelcomeScreen = preferences.showWelcomeScreen,
                        localeCode = preferences.localeCode
                    )
                    if (preferences.localeCode == LocaleCode.English.localeCode) {
                        newPreferences.localeCode = LocaleCode.Polish.localeCode
                    } else if (preferences.localeCode == LocaleCode.Polish.localeCode) {
                        newPreferences.localeCode = LocaleCode.English.localeCode
                    }
                    onClickUpdatePreferences(context, newPreferences)
                    refreshHomePage()
                }
            ) {
                EyebrowText(
                    text = stringResource(R.string.home_nav_bar_language_button),
                    modifier = Modifier.weight(1f)
                )
                val resourceId = if (preferences.localeCode == LocaleCode.English.localeCode) {
                    FlagKit.getResId(context, FlagTag.Polish.flagName)
                } else {
                    FlagKit.getResId(context, FlagTag.English.flagName)
                }
                Image(
                    painter = painterResource(resourceId),
                    contentDescription = stringResource(R.string.home_nav_bar_language_button),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Version text, displayed at the bottom of the dropdown.
            Divider()
            DropdownMenuItem(
                onClick = {},
                enabled = false
            ) {
                val versionName = GeneralUtil.versionName
                EyebrowText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Version: $versionName",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Light
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
            onClickViewWelcomePage = {},
            preferences = Preferences(),
            onClickUpdatePreferences = { _: Context, _: Preferences -> },
            refreshHomePage = {}
        )
    }
}