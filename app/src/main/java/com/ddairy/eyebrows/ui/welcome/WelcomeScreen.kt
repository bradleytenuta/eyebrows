package com.ddairy.eyebrows.ui.welcome

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    onGettingStartedClick: () -> Unit,
    onSkipClicked: () -> Unit
) {
    val welcomePages = listOf(
        WelcomePage(
            stringResource(R.string.welcome_page_1_title),
            stringResource(R.string.welcome_page_1_text),
            R.drawable.box
        ),
        WelcomePage(
            stringResource(R.string.welcome_page_2_title),
            stringResource(R.string.welcome_page_2_text),
            R.drawable.alarm
        )
    )

    val pagerState = rememberPagerState()
    Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
        TextButton(
            onClick = { onSkipClicked() },
            modifier = Modifier.padding(8.dp)
        ) {
            EyebrowText(text = stringResource(R.string.welcome_skip_button))
        }

        // Contains the page UI which appears as you swipe.
        HorizontalPager(
            count = welcomePages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { count ->
            WelcomePage(
                welcomePage = welcomePages[count],
                onGettingStartedClick = onGettingStartedClick,
                isLastPage = pagerState.currentPage == (welcomePages.size - 1)
            )
        }

        // The page indicator found at the bottom of the screen.
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = MaterialTheme.colors.secondary
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview("Welcome Screen")
@Composable
private fun WelcomePreview() {
    EyebrowsTheme {
        WelcomeScreen(
            onGettingStartedClick = {},
            onSkipClicked = {}
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview("Welcome Screen - Dark Mode")
@Composable
private fun WelcomePreviewDarkMode() {
    EyebrowsTheme(darkTheme = true) {
        WelcomeScreen(
            onGettingStartedClick = {},
            onSkipClicked = {}
        )
    }
}