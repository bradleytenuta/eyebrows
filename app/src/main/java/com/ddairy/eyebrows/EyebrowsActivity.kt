package com.ddairy.eyebrows

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.helper.FirebaseUtil
import com.ddairy.eyebrows.util.storage.InternalStorage
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.time.ExperimentalTime

// TODO: add Android tests. Days remaining might have a bug when deadline is large.
// TODO: Add adds
// TODO: Look into notifications for when deadline is reached for eyebrow.
// TODO: Improve app to work in Landscape mode. Then enable landscape mode.
// TODO: Add other language support.
// TODO: Add a menu icon with a menu item that takes you to the privacy policy.
// TODO: Add another welcome page explaining the origin of eyebrows.
// TODO: Add support for other firebase features like performance and crashanalytics. No policy changes needed.
/**
 * Class that runs the application.
 */
class EyebrowsActivity : ComponentActivity() {

    private val modelEyebrow by viewModels<EyebrowModel>()

    private var preferences: Preferences = Preferences()

    @ExperimentalTime
    @ExperimentalPagerApi
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Forces the application to only work in Portrait.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Initialise Analytics.
        FirebaseUtil.initialiseAnalytics()

        // Updates the list of eyebrows with internal storage.
        modelEyebrow.initialiseWithStorage(this)

        // Updates the preference object with internal storage.
        preferences = InternalStorage.readPreferences(this)

        installSplashScreen()
        setContent {
            EyebrowsTheme {
                Scaffold {
                    EyebrowsNavigation(
                        eyebrowModel = modelEyebrow,
                        preferences = preferences
                    )
                }
            }
        }
    }
}