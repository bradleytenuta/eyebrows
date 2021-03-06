package com.ddairy.eyebrows.ui.home

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.ui.components.AdvertView
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.home.components.HomeFilter
import com.ddairy.eyebrows.ui.home.components.HomeNavBar
import com.ddairy.eyebrows.ui.home.components.card.EyebrowCard
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.tag.HomeTab
import org.joda.time.LocalDate
import kotlin.time.ExperimentalTime

private const val offsetValue = -140

/**
 * The UI home screen. Used to display and configure eyebrows.
 */
@ExperimentalTime
@Composable
fun HomeScreen(
    onClickNewEyebrows: (Eyebrow) -> Unit,
    onClickViewWelcomePage: () -> Unit,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    selectedHomeTab: HomeTab,
    updateSelectedHomeTab: (homeTab: HomeTab) -> Unit,
    preferences: Preferences,
    onClickUpdatePreferences: (context: Context, preferences: Preferences) -> Unit,
    refreshHomePage: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .semantics {
                contentDescription = context.resources.getString(R.string.home_description)
            }
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        // A Box that appears at the top of the Home page to add something extra.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.secondaryVariant
                        )
                    )
                )
        ) {
            HomeNavBar(
                onClickNewEyebrows = { onClickNewEyebrows(Eyebrow(description = "")) },
                onClickViewWelcomePage = onClickViewWelcomePage,
                preferences = preferences,
                onClickUpdatePreferences = onClickUpdatePreferences,
                refreshHomePage = refreshHomePage
            )
        }
        EyebrowListContainer(
            onClickNewEyebrows = onClickNewEyebrows,
            eyebrows = eyebrows,
            removeEyebrow = removeEyebrow,
            updateEyebrow = updateEyebrow,
            selectedHomeTab = selectedHomeTab,
            updateSelectedHomeTab = updateSelectedHomeTab
        )
    }
}

@ExperimentalTime
@Composable
private fun EyebrowListContainer(
    onClickNewEyebrows: (Eyebrow) -> Unit,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    selectedHomeTab: HomeTab,
    updateSelectedHomeTab: (homeTab: HomeTab) -> Unit
) {
    val openEyebrows = eyebrows.filter { it.status == Eyebrow.Status.Open }
    val completeEyebrows = eyebrows.filter { it.status == Eyebrow.Status.Complete }
    HomeFilter(
        offsetValue = offsetValue.dp,
        homeTab = selectedHomeTab,
        NumberOfEyebrowsOpen = openEyebrows.size,
        NumberOfEyebrowsCompleted = completeEyebrows.size,
        onTabSelected = {
            updateSelectedHomeTab(it)
        })
    if (HomeTab.Open == selectedHomeTab) {
        if (openEyebrows.isNotEmpty()) {
            EyebrowList(
                onClickNewEyebrows = onClickNewEyebrows,
                eyebrows = openEyebrows,
                removeEyebrow = removeEyebrow,
                updateEyebrow = updateEyebrow
            )
        } else {
            NoEyebrowsFiller()
        }
    } else if (HomeTab.Completed == selectedHomeTab) {
        if (completeEyebrows.isNotEmpty()) {
            EyebrowList(
                onClickNewEyebrows = onClickNewEyebrows,
                eyebrows = completeEyebrows,
                removeEyebrow = removeEyebrow,
                updateEyebrow = updateEyebrow
            )
        } else {
            NoEyebrowsFiller()
        }
    }
}

@ExperimentalTime
@Composable
private fun EyebrowList(
    onClickNewEyebrows: (Eyebrow) -> Unit,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit
) {
    Column(
        modifier = Modifier.offset(y = offsetValue.dp)
    ) {
        eyebrows.forEachIndexed { index, eyebrow ->
            EyebrowCard(
                eyebrow = eyebrow,
                removeEyebrow = removeEyebrow,
                updateEyebrow = updateEyebrow,
                onClickNewEyebrows = onClickNewEyebrows
            )
            // Displays an advert after first eyebrow, then after every 2 eyebrows.
            if (index == 0 || index > 1) {
                AdvertView()
            }
        }
    }
}

@Composable
private fun NoEyebrowsFiller(
    modifier: Modifier = Modifier
) {
    // Adjusts layout based on orientation.
    val configuration = LocalConfiguration.current
    val columnPaddingValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { PaddingValues(horizontal = 32.dp, vertical = 0.dp) } else -> { PaddingValues(32.dp) }
    }
    val spacerOnePaddingValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 10.dp } else -> { 50.dp }
    }
    val imageSize = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 150.dp } else -> { 200.dp }
    }
    val spacerTwoPaddingValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 10.dp } else -> { 20.dp }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(columnPaddingValues)
            .fillMaxWidth()
            .offset(y = offsetValue.dp)
    ) {
        Spacer(modifier = Modifier.height(spacerOnePaddingValues))
        Image(
            painter = painterResource(R.drawable.home_screen_face),
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Spacer(modifier = Modifier.height(spacerTwoPaddingValues))
        EyebrowText(
            text = stringResource(R.string.home_no_eyebrows_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )
    }
}

@ExperimentalTime
@Preview("Home Screen")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        HomeScreen(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {},
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDate.now(),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDate.now().plusDays(3)
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDate.now().plusDays(1)
                )
            ),
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> },
            selectedHomeTab = HomeTab.Open,
            updateSelectedHomeTab = {},
            preferences = Preferences(),
            onClickUpdatePreferences = { _: Context, _: Preferences -> },
            refreshHomePage = {}
        )
    }
}

@ExperimentalTime
@Preview("Home Screen - Dark Mode")
@Composable
private fun HomePreviewDarkMode() {
    EyebrowsTheme(darkTheme = true) {
        HomeScreen(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {},
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDate.now().plusDays(1),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDate.now().minusDays(1),
                )
            ),
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> },
            selectedHomeTab = HomeTab.Open,
            updateSelectedHomeTab = {},
            preferences = Preferences(),
            onClickUpdatePreferences = { _: Context, _: Preferences -> },
            refreshHomePage = {}
        )
    }
}

@ExperimentalTime
@Preview("Home Screen - No Eyebrows")
@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360, name = "Home Screen - No Eyebrows")
@Composable
private fun HomePreviewNoEyebrows() {
    EyebrowsTheme {
        HomeScreen(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {},
            eyebrows = emptyList(),
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> },
            selectedHomeTab = HomeTab.Open,
            updateSelectedHomeTab = {},
            preferences = Preferences(),
            onClickUpdatePreferences = { _: Context, _: Preferences -> },
            refreshHomePage = {}
        )
    }
}