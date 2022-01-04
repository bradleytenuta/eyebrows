package com.ddairy.eyebrows

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.eyebrow.EyebrowScreen
import com.ddairy.eyebrows.ui.home.HomeScreen
import com.ddairy.eyebrows.util.tag.ScreenName
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun EyebrowsNavigation(
    eyebrowModel: EyebrowModel,
    navController: NavHostController = rememberAnimatedNavController(),
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = ScreenName.Overview.route
    ) {
        composable(route = ScreenName.Overview.route) {
            HomeScreen(
                onClickNewEyebrows = { eyebrow ->
                    val eyebrowRoute = ScreenName.NewName.route
                    val newRoute = eyebrowRoute.replace(ScreenName.NewName.argument, eyebrow.id.toString())
                    navController.navigate(newRoute)
                },
                eyebrows = eyebrowModel.eyebrows,
                removeEyebrow = eyebrowModel::removeEyebrow,
                updateEyebrow = eyebrowModel::updateEyebrow
            )
        }
        composable(
            route = ScreenName.NewName.route,
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
                onClickReturnHome = { navController.navigate(ScreenName.Overview.route) },
                eyebrow = eyebrow,
                addEyebrow = eyebrowModel::addEyebrow,
            )
        }
    }
}