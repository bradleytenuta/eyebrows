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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.model.ModelEyebrow
import com.google.accompanist.navigation.animation.composable
import com.ddairy.eyebrows.ui.home.HomeBody
import com.ddairy.eyebrows.ui.stake.NewEyebrowsBody
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.EyebrowsScreen
import com.ddairy.eyebrows.util.InstanceMapper
import com.ddairy.eyebrows.util.LocalDataStore
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

class EyebrowsActivity : ComponentActivity() {

    private val modelEyebrow by viewModels<ModelEyebrow>()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EyebrowsApp(
                lifeCycle = lifecycle,
                modelEyebrow = modelEyebrow
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun EyebrowsApp(
    lifeCycle: Lifecycle,
    modelEyebrow: ModelEyebrow
) {
    val localDataStore = LocalDataStore(LocalContext.current)

    // Updates the eyebrows in the model with whats saved locally.
    val json = localDataStore.localEyebrows.collectAsState(initial = "[]").value!!
    modelEyebrow.initialiseWithLocalEyebrows(InstanceMapper.mapper.readValue(json))

    // Updates the local data when the application is closed.
    val scope = rememberCoroutineScope()
    lifeCycle.addObserver(LifecycleEventObserver { source, event ->
        // TODO: seemed to have work first time, however each other time the local property does
        // TODO: not seem to get updated. This does get called but the properties in datastore does not get updated.
        if (event == Lifecycle.Event.ON_DESTROY || event == Lifecycle.Event.ON_STOP) {
            scope.launch {
                localDataStore.writeLocalEyebrows(modelEyebrow.eyebrows)
            }
        }
    })

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