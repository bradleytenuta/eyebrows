package com.ddairy.eyebrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.model.ModelEyebrow
import com.ddairy.eyebrows.ui.eyebrow.EyebrowScreen
import com.ddairy.eyebrows.ui.home.HomeScreen
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.tag.ScreenName
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

// TODO: Refactor this file.
// TODO: reduce dependencies, test.
class EyebrowsActivity : ComponentActivity() {

    private val modelEyebrow by viewModels<ModelEyebrow>()

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Updates the list of eyebrows with whats in internal storage.
        modelEyebrow.initialiseWithLocalEyebrows(this)

        installSplashScreen()
        setContent {
            EyebrowsApp(modelEyebrow = modelEyebrow)
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun EyebrowsApp(modelEyebrow: ModelEyebrow) {
    EyebrowsTheme {
        Scaffold { innerPadding ->
            EyebrowsNavHost(
                navController = rememberAnimatedNavController(),
                modelEyebrow = modelEyebrow,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun EyebrowsNavHost(
    navController: NavHostController,
    modelEyebrow: ModelEyebrow,
    modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = ScreenName.Overview.route,
        modifier = modifier
    ) {
        composable(route = ScreenName.Overview.route) {
            HomeScreen(
                onClickNewEyebrows = { eyebrow ->
                    val eyebrowRoute = ScreenName.NewName.route
                    val newRoute = eyebrowRoute.replace(ScreenName.NewName.argument, eyebrow.id.toString())
                    navController.navigate(newRoute)
                },
                eyebrows = modelEyebrow.eyebrows,
                removeEyebrow = modelEyebrow::removeEyebrow,
                updateEyebrow = modelEyebrow::updateEyebrow
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
                eyebrow = modelEyebrow.eyebrows.find { eyebrow -> eyebrow.id.toString() == eyebrowUUID }
            }
            if (eyebrow == null) {
                eyebrow = Eyebrow(description = "")
            }

            EyebrowScreen(
                onClickReturnHome = { navController.navigate(ScreenName.Overview.route) },
                eyebrow = eyebrow,
                addEyebrow = modelEyebrow::addEyebrow,
            )
        }
    }
}