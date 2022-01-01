package com.ddairy.eyebrows.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val darkColorPalette = darkColors(
    primary = PrimaryYellow,
    primaryVariant = PrimaryVariantYellow,
    secondary = SecondaryOrange,
    secondaryVariant = SecondaryVariantOrange
)

private val lightColorPalette = lightColors(
    primary = PrimaryYellow,
    primaryVariant = PrimaryVariantYellow,
    secondary = SecondaryOrange,
    secondaryVariant = SecondaryVariantOrange
)

@Composable
fun EyebrowsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) darkColorPalette else lightColorPalette,
        content = content
    )
}