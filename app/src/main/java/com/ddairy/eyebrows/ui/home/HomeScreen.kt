package com.ddairy.eyebrows.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.ui.home.components.card.EyebrowCard
import com.ddairy.eyebrows.ui.home.components.HomeNavBar
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.ddairy.eyebrows.util.helper.EyebrowUtil
import java.time.LocalDateTime

/**
 * The UI home screen. Used to display and configure eyebrows.
 */
@Composable
fun HomeScreen(
    onClickNewEyebrows: (Eyebrow) -> Unit,
    onClickViewWelcomePage: () -> Unit,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit
) {
    Column(
        modifier = Modifier
            .semantics { contentDescription = "Home Screen" }
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        // A Box that appears at the top of the Home page to add something extra.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.secondaryVariant
                        )
                    )
                )
        ) {
            HomeNavBar(
                onClickNewEyebrows = { onClickNewEyebrows(Eyebrow(description = "")) },
                onClickViewWelcomePage = onClickViewWelcomePage
            )
        }

        // Displays eyebrows list or a 'no eyebrows' message.
        if (eyebrows.isNotEmpty()) {
            EyebrowList(
                onClickNewEyebrows = onClickNewEyebrows,
                eyebrows = eyebrows,
                removeEyebrow = removeEyebrow,
                updateEyebrow = updateEyebrow
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your eyebrows need a trim, how about a friendly bet?",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}

@Composable
fun EyebrowList(
    onClickNewEyebrows: (Eyebrow) -> Unit,
    eyebrows: List<Eyebrow>,
    removeEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit,
    updateEyebrow: (context: Context, eyebrow: Eyebrow) -> Unit
) {
    Column(
        modifier = Modifier.offset(y = (-140).dp)
    ) {
        val organisedList = EyebrowUtil.organiseList(eyebrows)
        organisedList.forEachIndexed { index, eyebrow ->
            EyebrowCard(
                eyebrow = eyebrow,
                removeEyebrow = removeEyebrow,
                updateEyebrow = updateEyebrow,
                onClickNewEyebrows = onClickNewEyebrows
            )

            // Creates a divider between the open and completed eyebrows.
            val newIndex = index + 1
            if (
                newIndex < organisedList.size &&
                eyebrow.status == Eyebrow.Status.Open &&
                organisedList[newIndex].status == Eyebrow.Status.Complete
            ) {
                Divider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 2.dp
                )
            }
        }
    }
}


@Preview("Home Screen")
@Composable
private fun HomePreview() {
    EyebrowsTheme {
        HomeScreen(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {},
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now(),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now().plusDays(3)
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now().plusDays(1)
                )
            ),
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> }
        )
    }
}

@Preview("Home Screen - Dark Mode")
@Composable
private fun HomePreviewDarkMode() {
    EyebrowsTheme(darkTheme = true) {
        HomeScreen(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {},
            eyebrows = listOf(
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now().plusDays(1),
                    status = Eyebrow.Status.Complete
                ),
                Eyebrow(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    endDate = LocalDateTime.now().minusDays(1),
                )
            ),
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> }
        )
    }
}

@Preview("Home Screen - No Eyebrows")
@Composable
private fun HomePreviewNoEyebrows() {
    EyebrowsTheme {
        HomeScreen(
            onClickNewEyebrows = {},
            onClickViewWelcomePage = {},
            eyebrows = emptyList(),
            removeEyebrow = { _: Context, _: Eyebrow -> },
            updateEyebrow = { _: Context, _: Eyebrow -> }
        )
    }
}