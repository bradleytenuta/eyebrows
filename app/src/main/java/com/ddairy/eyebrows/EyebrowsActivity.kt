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
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.ddairy.eyebrows.model.ModelEyebrow
import com.google.accompanist.navigation.animation.composable
import com.ddairy.eyebrows.model.ModelLightMode
import com.ddairy.eyebrows.ui.home.HomeBody
import com.ddairy.eyebrows.ui.stake.NewEyebrowsBody
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.EyebrowsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class EyebrowsActivity : ComponentActivity() {

    private val lightModeModel by viewModels<ModelLightMode>()
    private val eyebrowModel by viewModels<ModelEyebrow>()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EyebrowsApp(
                modelLightMode = lightModeModel,
                modelEyebrow = eyebrowModel
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun EyebrowsApp(
    modelLightMode: ModelLightMode,
    modelEyebrow: ModelEyebrow
) {
    EyebrowsTheme(
        isLight = modelLightMode.isLightMode()
    ) {
        Scaffold { innerPadding ->
            EyebrowsNavHost(
                navController = rememberAnimatedNavController(),
                modelLightMode = modelLightMode,
                modelEyebrow = modelEyebrow,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun EyebrowsNavHost(
    navController: NavHostController,
    modelLightMode: ModelLightMode,
    modelEyebrow: ModelEyebrow,
    modifier: Modifier = Modifier
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = EyebrowsScreen.Overview.name,
        modifier = modifier
    ) {
        composable(route = EyebrowsScreen.Overview.name) {
            HomeBody(
                onClickNewEyebrows = { navController.navigate(EyebrowsScreen.NewEyebrows.name) },
                onSwitchTheme = { modelLightMode.toggleLightMode() },
                isLightMode = modelLightMode.isLightMode(),
                eyebrows = modelEyebrow.eyebrows,
                removeEyebrow = modelEyebrow::removeEyebrow,
                updateEyebrow = modelEyebrow::updateEyebrow
            )
        }
        composable(
            route = EyebrowsScreen.NewEyebrows.name,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 })
            },
        ) {
            NewEyebrowsBody(
                onClickReturnHome = { navController.navigate(EyebrowsScreen.Overview.name) },
                addEyebrow = modelEyebrow::addEyebrow,
            )
        }
    }
}