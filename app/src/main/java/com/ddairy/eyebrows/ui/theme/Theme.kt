package com.ddairy.eyebrows.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryYellow,
    primaryVariant = PrimaryVariantYellow,
    secondary = SecondaryOrange,
    secondaryVariant = SecondaryVariantOrange
)

private val LightColorPalette = lightColors(
    primary = PrimaryYellow,
    primaryVariant = PrimaryVariantYellow,
    secondary = SecondaryOrange,
    secondaryVariant = SecondaryVariantOrange
)

@Composable
fun EyebrowsTheme(
    isLight: Boolean = !isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isLight) {
        LightColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}