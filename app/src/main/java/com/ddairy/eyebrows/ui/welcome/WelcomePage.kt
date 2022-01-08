package com.ddairy.eyebrows.ui.welcome

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
    onGettingStartedClick:() -> Unit,
    isLastPage: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(welcomePage.image),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        EyebrowText(
            text = welcomePage.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(8.dp))
        EyebrowText(
            text = welcomePage.description,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))

        // When on the last page, show the button with animation.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 80.dp)
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