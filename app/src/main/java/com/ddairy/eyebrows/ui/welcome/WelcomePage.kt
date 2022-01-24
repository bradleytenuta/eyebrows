package com.ddairy.eyebrows.ui.welcome

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText

data class WelcomePage(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

/**
 * The UI for a certain welcome page.
 */
@Composable
fun WelcomePage(
    welcomePage: WelcomePage,
    onGettingStartedClick: () -> Unit,
    isLastPage: Boolean = false
) {
    val configuration = LocalConfiguration.current
    val columnPaddingValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { PaddingValues(horizontal = 16.dp, vertical = 0.dp) } else -> { PaddingValues(16.dp) }
    }
    val imageSize = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 150.dp } else -> { 200.dp }
    }
    val spacerOneValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 5.dp } else -> { 20.dp }
    }
    val spacerTwoValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 4.dp } else -> { 8.dp }
    }
    val spacerThreeValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 10.dp } else -> { 40.dp }
    }
    val buttonValues = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> { 0.dp } else -> { 80.dp }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(columnPaddingValues)
    ) {
        Image(
            painter = painterResource(welcomePage.image),
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Spacer(modifier = Modifier.height(spacerOneValues))
        EyebrowText(
            text = welcomePage.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(spacerTwoValues))
        EyebrowText(
            text = welcomePage.description,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(spacerThreeValues))

        // When on the last page, show the button with animation.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = buttonValues)
                .padding(8.dp)
        ) {
            AnimatedVisibility(
                visible = isLastPage,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    shape = RoundedCornerShape(20.dp),
                    onClick = onGettingStartedClick
                ) {
                    EyebrowText(text = stringResource(R.string.welcome_start_button))
                }
            }
        }
    }
}