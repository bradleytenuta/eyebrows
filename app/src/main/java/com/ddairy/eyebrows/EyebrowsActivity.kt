package com.ddairy.eyebrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ddairy.eyebrows.model.EyebrowModel
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.helper.FirebaseUtil
import com.ddairy.eyebrows.util.helper.GeneralUtil
import com.ddairy.eyebrows.util.notification.NotificationUtil
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.ads.MobileAds
import kotlin.time.ExperimentalTime

// TODO: Look into using horizontal pager for the tabs once we can disable scroll, or some animation to switch tabs and when deleting/moving an eyebrow.
/**
 * Class that runs the application.
 */
class EyebrowsActivity : ComponentActivity() {

    private val modelEyebrow by viewModels<EyebrowModel>()

    @ExperimentalTime
    @ExperimentalPagerApi
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}

        // Creates a notification channel and stores it.
        NotificationUtil.initialise(this)

        // Initialise Analytics.
        FirebaseUtil.initialiseAnalytics()

        // Updates the list of eyebrows and preferences with internal storage.
        modelEyebrow.initialiseEyebrowsWithStorage(this)
        modelEyebrow.initialisePreferencesWithStorage(this)

        // Updates app util and stores the version name.
        val versionName = this.packageManager.getPackageInfo(this.packageName, 0).versionName
        if (versionName != null) {
            GeneralUtil.versionName = versionName
        }

        installSplashScreen()
        setContent {
            EyebrowsTheme {
                Scaffold {
                    EyebrowsNavigation(eyebrowModel = modelEyebrow)
                }
            }
        }
    }
}