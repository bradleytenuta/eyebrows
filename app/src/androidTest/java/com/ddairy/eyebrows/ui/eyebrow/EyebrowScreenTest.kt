package com.ddairy.eyebrows.ui.eyebrow

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.ddairy.eyebrows.EyebrowsNavigation
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class EyebrowScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext;

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun createEyebrowWithoutParticipants() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_description_field_label))
            .performTextInput("test")
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun createEyebrowWithParticipants() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_description_field_label))
            .performTextInput("test")
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_participant_label))
            .performTextInput("participant")
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun cancelMakingEyebrow() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_nav_bar_cancel))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun createEyebrowFailAsNoDescription() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_description_error_message))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    @Ignore // TODO: Fix, cannot be done as not sure how to test date picker in jetpack compose. Could try and mock eyebrow to have past date instead.
    fun createEyebrowFailAsDeadlineInPast() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_date_error_message))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    @Ignore // TODO: Fix, cannot be done as not sure how to test date picker in jetpack compose. Could try and mock eyebrow to have past date instead.
    fun createEyebrowFailAsNoDescriptionAndDeadlineInPast() {
        val errorOne = context.resources.getString(R.string.eyebrow_description_error_message)
        val errorTwo = context.resources.getString(R.string.eyebrow_date_error_message)

        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText("$errorOne $errorTwo")
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    private fun initialise(showWelcomeScreen: Boolean = false) {
        val eyebrowModel = EyebrowModel()
        eyebrowModel.preferences.showWelcomeScreen = showWelcomeScreen
        composeTestRule.setContent {
            EyebrowsTheme {
                Scaffold {
                    EyebrowsNavigation(
                        eyebrowModel = eyebrowModel
                    )
                }
            }
        }
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_nav_bar_title))
            .assertIsDisplayed()
    }
}