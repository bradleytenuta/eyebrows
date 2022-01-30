package com.ddairy.eyebrows.ui.welcome

import androidx.compose.ui.test.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry
import com.ddairy.eyebrows.EyebrowsNavigation
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val preferences = Preferences(showWelcomeScreen = true)
    private val context = InstrumentationRegistry.getInstrumentation().targetContext;

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun isWelcomePageDisplayed() {
        preferences.showWelcomeScreen = true
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_skip_button))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun skipWelcomePage() {
        preferences.showWelcomeScreen = false
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_skip_button))
            .assertDoesNotExist()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canSkipWelcomeScreen() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_skip_button))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun doesNotGoBackToWelcomeScreen() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_skip_button))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
        Espresso.pressBackUnconditionally()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_skip_button))
            .assertDoesNotExist()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun doesShowAllScreens() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_page_1_title))
            .assertIsDisplayed()
        composeTestRule.onRoot().performGesture { swipeLeft() }
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_page_2_title))
            .assertIsDisplayed()
        composeTestRule.onRoot().performGesture { swipeLeft() }
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_page_3_title))
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithText(context.resources.getString(R.string.welcome_start_button))[0]
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    private fun initialise() {
        composeTestRule.setContent {
            EyebrowsTheme {
                Scaffold {
                    EyebrowsNavigation(
                        eyebrowModel = EyebrowModel()
                    )
                }
            }
        }
    }
}