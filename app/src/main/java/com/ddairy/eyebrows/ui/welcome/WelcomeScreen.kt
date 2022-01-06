package com.ddairy.eyebrows.ui.welcome

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

// TODO: Write this and make pictures.
val welcomePages = listOf(
    WelcomePage(
        "Easy Momo Transfer",
        "Make a quick transaction with someone besides you by scanning their Easy Momo Code",
        R.drawable.ic_launcher_foreground
    ),
    WelcomePage(
        "Easy Select",
        "Pick a contact directly from your contact list and Momo them in less than no time",
        R.drawable.ic_launcher_foreground
    ),
    WelcomePage(
        "Secure",
        "We will never know your Momo PIN in anyway.The app does not share your data with any third party. No internet connection required",
        R.drawable.ic_launcher_foreground
    )
)

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    onGettingStartedClick:() -> Unit,
    onSkipClicked:() -> Unit
) {
    val pagerState = rememberPagerState()
    Column(modifier = Modifier.background(Color.Transparent)) {
        TextButton(
            onClick = { onSkipClicked() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Skip")
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