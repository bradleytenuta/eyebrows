package com.ddairy.eyebrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddairy.eyebrows.ui.components.LightModeModel
import com.ddairy.eyebrows.ui.home.HomeBody
import com.ddairy.eyebrows.ui.stake.NewEyebrowsBody
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

class EyebrowsActivity : ComponentActivity() {

    private val eyebrowsViewModel by viewModels<LightModeModel>()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EyebrowsApp(eyebrowsViewModel)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun EyebrowsApp(lightModeModel: LightModeModel) {
    EyebrowsTheme(
        isLight = lightModeModel.isLightMode()
    ) {
        Scaffold { innerPadding ->
            EyebrowsNavHost(
                navController = rememberNavController(),
                lightModeModel = lightModeModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun EyebrowsNavHost(
    navController: NavHostController,
    lightModeModel: LightModeModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = EyebrowsScreen.Overview.name,
        modifier = modifier
    ) {
        composable(EyebrowsScreen.Overview.name) {
            HomeBody(
                onClickNewEyebrows = { navController.navigate(EyebrowsScreen.NewEyebrows.name) },
                onSwitchTheme = { lightModeModel.toggleLightMode() },
                isLightMode = lightModeModel.isLightMode()
            )
        }
        composable(EyebrowsScreen.NewEyebrows.name) {
            NewEyebrowsBody(
                onClickReturnHome = { navController.navigate(EyebrowsScreen.Overview.name) }
            )
        }
    }
}