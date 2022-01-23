package com.ddairy.eyebrows.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
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

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val preferences = Preferences(showWelcomeScreen = false)
    private val context = InstrumentationRegistry.getInstrumentation().targetContext;

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canCreateNewEyebrow() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_nav_bar_title))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canEditExistingEyebrow() {
        initialise()
        composeTestRule.onNodeWithContentDescription(context.resources.getString(R.string.home_action_more_options_icon_description))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_action_dropdown_edit))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_nav_bar_title))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_description_field_label))
            .performTextClearance()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_description_field_label))
            .performTextInput("test2")
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("test2")
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canDeleteEyebrow() {
        initialise()
        composeTestRule.onNodeWithContentDescription(context.resources.getString(R.string.home_action_more_options_icon_description))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_action_dropdown_remove))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_card_remove_eyebrow_dialog_delete))
            .performClick()
        composeTestRule.onNodeWithText("test1")
            .assertDoesNotExist()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canMarkEyebrowAsDone() {
        initialise()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_action_open_button))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_action_open_button))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_action_complete_button))
            .assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canViewParticipants() {
        initialise()
        composeTestRule.onNodeWithText("participant")
            .assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(context.resources.getString(R.string.home_card_eyebrow_participants_dialog_title))
            .performClick()
        composeTestRule.onNodeWithText("participant")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_card_eyebrow_participants_dialog_close))
            .performClick()
        composeTestRule.onNodeWithText("participant")
            .assertDoesNotExist()
    }

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @Test
    fun canGoToWelcomeScreen() {
        initialise()
        composeTestRule.onNodeWithContentDescription(context.resources.getString(R.string.home_nav_bar_help_icon_description))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.welcome_skip_button))
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
                        eyebrowModel = EyebrowModel(),
                        preferences = preferences
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(context.resources.getString(R.string.home_nav_bar_new_eyebrow))
            .performClick()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_nav_bar_title))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_description_field_label))
            .performTextInput("test1")
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_participant_label))
            .performTextInput("participant")
        composeTestRule.onNodeWithText(context.resources.getString(R.string.eyebrow_save_section))
            .performClick()
        composeTestRule.onNodeWithText("test1")
            .assertIsDisplayed()
    }
}