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
import com.google.accompanist.navigation.animation.composable
import com.ddairy.eyebrows.ui.home.HomeBody
import com.ddairy.eyebrows.ui.stake.NewEyebrowsBody
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.EyebrowsScreen
import com.ddairy.eyebrows.util.InternalStorage
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class EyebrowsActivity : ComponentActivity() {

    private val modelEyebrow by viewModels<ModelEyebrow>()

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Updates the list of eyebrows with whats in internal storage.
        modelEyebrow.initialiseWithLocalEyebrows(InternalStorage.readEyebrows(this))

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
        startDestination = EyebrowsScreen.Overview.route,
        modifier = modifier
    ) {
        composable(route = EyebrowsScreen.Overview.route) {
            HomeBody(
                onClickNewEyebrows = { eyebrow ->
                    var eyebrowRoute = EyebrowsScreen.NewEyebrows.route
                    var newRoute = eyebrowRoute.replace(EyebrowsScreen.NewEyebrows.argument, eyebrow.id.toString())
                    navController.navigate(newRoute)
                },
                eyebrows = modelEyebrow.eyebrows,
                removeEyebrow = modelEyebrow::removeEyebrow,
                updateEyebrow = modelEyebrow::updateEyebrow
            )
        }
        composable(
            route = EyebrowsScreen.NewEyebrows.route,
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

            NewEyebrowsBody(
                onClickReturnHome = { navController.navigate(EyebrowsScreen.Overview.route) },
                eyebrow = eyebrow,
                addEyebrow = modelEyebrow::addEyebrow,
            )
        }
    }
}