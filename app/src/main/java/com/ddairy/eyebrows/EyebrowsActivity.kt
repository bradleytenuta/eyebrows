package com.ddairy.eyebrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.ddairy.eyebrows.model.EyebrowModel
import com.google.accompanist.navigation.animation.composable
import com.ddairy.eyebrows.model.LightModeModel
import com.ddairy.eyebrows.ui.home.HomeBody
import com.ddairy.eyebrows.ui.stake.NewEyebrowsBody
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class EyebrowsActivity : ComponentActivity() {

    private val lightModeModel by viewModels<LightModeModel>()
    private val eyebrowModel by viewModels<EyebrowModel>()

    @ExperimentalAnimationApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EyebrowsApp(
                lightModeModel = lightModeModel,
                eyebrowModel = eyebrowModel
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun EyebrowsApp(
    lightModeModel: LightModeModel,
    eyebrowModel: EyebrowModel
) {
    EyebrowsTheme(
        isLight = lightModeModel.isLightMode()
    ) {
        Scaffold { innerPadding ->
            EyebrowsNavHost(
                navController = rememberAnimatedNavController(),
                lightModeModel = lightModeModel,
                eyebrowModel = eyebrowModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun EyebrowsNavHost(
    navController: NavHostController,
    lightModeModel: LightModeModel,
    eyebrowModel: EyebrowModel,
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
                onSwitchTheme = { lightModeModel.toggleLightMode() },
                isLightMode = lightModeModel.isLightMode(),
                eyebrows = eyebrowModel.eyebrows,
            )
        }
        composable(
            route = EyebrowsScreen.NewEyebrows.name,
            enterTransition = { initial, _ ->
                slideInHorizontally(initialOffsetX = { 1000 })
            },
            exitTransition = { _, target ->
                slideOutHorizontally(targetOffsetX = { 1000 })
            },
        ) {
            NewEyebrowsBody(
                onClickReturnHome = { navController.navigate(EyebrowsScreen.Overview.name) },
                addEyebrow = eyebrowModel::addEyebrow,
            )
        }
    }
}