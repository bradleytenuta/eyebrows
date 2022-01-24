package com.ddairy.eyebrows

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
import com.ddairy.eyebrows.util.helper.GeneralUtil
import com.ddairy.eyebrows.util.notification.NotificationUtil
import com.ddairy.eyebrows.util.storage.InternalStorage
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.time.ExperimentalTime

// TODO: Add adds
// TODO: Add other language support.
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

        // Creates a notification channel and stores it.
        NotificationUtil.initialise(this)

        // Initialise Analytics.
        FirebaseUtil.initialiseAnalytics()

        // Updates the list of eyebrows with internal storage.
        modelEyebrow.initialiseWithStorage(this)

        // Updates the preference object with internal storage.
        preferences = InternalStorage.readPreferences(this)

        // Updates app util and stores the version name.
        val versionName = this.packageManager.getPackageInfo(this.packageName, 0).versionName
        if (versionName != null) {
            GeneralUtil.versionName = versionName
        }

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