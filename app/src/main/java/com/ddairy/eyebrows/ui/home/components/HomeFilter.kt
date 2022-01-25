package com.ddairy.eyebrows.ui.home.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ddairy.eyebrows.ui.theme.EyebrowRed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.util.tag.HomeTab

@Composable
fun HomeFilter(
    offsetValue: Dp,
    homeTab: HomeTab,
    onTabSelected: (homeTab: HomeTab) -> Unit,
    NumberOfEyebrowsOpen: Int,
    NumberOfEyebrowsCompleted: Int
) {
    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = offsetValue),
        selectedTabIndex = homeTab.ordinal,
        backgroundColor = Color.Transparent,
        divider = {},
        indicator = { tabPositions ->
            HomeTabIndicator(tabPositions, homeTab)
        }
    ) {
        FilterButton(onClick = { onTabSelected(HomeTab.Open) }, buttonText = HomeTab.Open.name, NumberOfEyebrows = NumberOfEyebrowsOpen)
        FilterButton(onClick = { onTabSelected(HomeTab.Completed) }, buttonText = HomeTab.Completed.name, NumberOfEyebrows = NumberOfEyebrowsCompleted)
    }
}

/**
 * Shows an indicator for the tab.
 *
 * @param tabPositions The list of [TabPosition]s from a [TabRow].
 * @param homeTab The [HomeTab] that is currently selected.
 */
@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    homeTab: HomeTab
) {
    val transition = updateTransition(targetState = homeTab, label = "Tab indicator")
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            if (HomeTab.Open isTransitioningTo HomeTab.Completed) {
                // Indicator moves to the right.
                // Low stiffness spring for the left edge so it moves slower than the right edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                // Indicator moves to the left.
                // Medium stiffness spring for the left edge so it moves faster than the right edge.
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = "Indicator left"
    ) { tab ->
        tabPositions[tab.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (HomeTab.Open isTransitioningTo HomeTab.Completed) {
                // Indicator moves to the right
                // Medium stiffness spring for the right edge so it moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // Low stiffness spring for the right edge so it moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { tab ->
        tabPositions[tab.ordinal].right
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, MaterialTheme.colors.primary),
                RoundedCornerShape(4.dp)
            )
    )
}

/**
 * The button within each tab.
 */
@Composable
fun FilterButton(
    onClick: () -> Unit,
    buttonText : String,
    NumberOfEyebrows: Int
) {
    TextButton(onClick = onClick) {
        EyebrowText(
            text = buttonText,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.width(5.dp))
        EyebrowText(
            text = NumberOfEyebrows.toString(),
            style = MaterialTheme.typography.subtitle1,
            color = EyebrowRed
        )
    }
}