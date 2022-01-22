package com.ddairy.eyebrows.ui.welcome

import androidx.compose.ui.test.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createComposeRule
import com.ddairy.eyebrows.EyebrowsNavigation
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val preferences = Preferences(showWelcomeScreen = true)

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Before
    fun setUp() {
        composeTestRule.setContent {
            EyebrowsTheme {
                Scaffold {
                    EyebrowsNavigation(
                        eyebrowModel = EyebrowModel(),
                        preferences = preferences
                    )
                }
            }
        }
    }

    @Test
    fun isWelcomePageDisplayed() {
        preferences.showWelcomeScreen = true
        // TODO: Can we get strings from string.xml file instead?
        composeTestRule.onNodeWithText("Friendly bets with friends")
            .assertIsDisplayed()
    }

    @Test
    fun canSkipWelcomeScreen() {
        composeTestRule.onNodeWithText("Skip")
            .performClick()
        composeTestRule.onNodeWithText("New Eyebrow")
            .assertIsDisplayed()
    }
}