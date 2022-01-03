package com.ddairy.eyebrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ddairy.eyebrows.model.ModelEyebrow
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme

// TODO: test.
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
            EyebrowsTheme {
                Scaffold {
                    EyebrowsNavigation(modelEyebrow = modelEyebrow)
                }
            }
        }
    }
}