package com.ddairy.eyebrows.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The default navbar used for this application.
 */
@Composable
fun NavBar(
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = title,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = actions,
        modifier = Modifier.padding(horizontal = 6.dp)
    )
}