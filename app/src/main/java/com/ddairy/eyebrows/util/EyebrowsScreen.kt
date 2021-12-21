package com.ddairy.eyebrows

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.PieChart

enum class EyebrowsScreen(
    val icon: ImageVector,
) {
    Overview(
        icon = Icons.Filled.PieChart,
    ),
    NewEyebrows(
        icon = Icons.Filled.AttachMoney,
    );

    companion object {
        fun fromRoute(route: String?): EyebrowsScreen =
            when (route?.substringBefore("/")) {
                NewEyebrows.name -> NewEyebrows
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}