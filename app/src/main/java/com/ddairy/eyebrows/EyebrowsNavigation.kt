package com.ddairy.eyebrows

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.eyebrow.EyebrowScreen
import com.ddairy.eyebrows.ui.home.HomeScreen
import com.ddairy.eyebrows.ui.welcome.WelcomeScreen
import com.ddairy.eyebrows.util.storage.InternalStorage
import com.ddairy.eyebrows.util.tag.ScreenName
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun EyebrowsNavigation(
    eyebrowModel: EyebrowModel,
    preferences: Preferences,
    navController: NavHostController = rememberAnimatedNavController(),
) {
    val startDestination = if (preferences.showWelcomeScreen) ScreenName.Welcome.route else ScreenName.Home.route
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ScreenName.Welcome.route) {
            val context = LocalContext.current
            val toHomeScreen = {
                // Sets preferences and writes to storage.
                preferences.showWelcomeScreen = false
                InternalStorage.writePreferences(context, preferences)

                // Removes this composable from the back stack, so when the page changes, the user cannot go back.
                navController.popBackStack()
                navController.navigate(ScreenName.Home.route)
            }
            WelcomeScreen(
                onGettingStartedClick = toHomeScreen,
                onSkipClicked = toHomeScreen
            )
        }
        composable(route = ScreenName.Home.route) {
            HomeScreen(
                onClickNewEyebrows = { eyebrow ->
                    val eyebrowRoute = ScreenName.Eyebrow.route
                    val newRoute = eyebrowRoute.replace(ScreenName.Eyebrow.argument, eyebrow.id.toString())
                    navController.navigate(newRoute)
                },
                onClickViewWelcomePage = {
                    navController.navigate(ScreenName.Welcome.route)
                },
                eyebrows = eyebrowModel.eyebrows,
                removeEyebrow = eyebrowModel::removeEyebrow,
                updateEyebrow = eyebrowModel::updateEyebrow
            )
        }
        composable(
            route = ScreenName.Eyebrow.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 })
            },
        ) { backStackEntry ->
            var eyebrow: Eyebrow? = null
            if (backStackEntry.arguments != null) {
                val eyebrowUUID = backStackEntry.arguments?.getString("id")
                eyebrow = eyebrowModel.eyebrows.find { eyebrow -> eyebrow.id.toString() == eyebrowUUID }
            }
            if (eyebrow == null) {
                eyebrow = Eyebrow(description = "")
            }

            EyebrowScreen(
                onClickReturnHome = {
                    // Removes this composable from the back stack, so when the page changes, the user cannot go back.
                    navController.popBackStack()
                    navController.navigate(ScreenName.Home.route)
                },
                eyebrow = eyebrow,
                addEyebrow = eyebrowModel::addEyebrow,
            )
        }
    }
}