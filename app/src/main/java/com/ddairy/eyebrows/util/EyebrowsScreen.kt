package com.ddairy.eyebrows.util

enum class EyebrowsScreen(
    val route: String,
    val argument: String = ""
) {
    Overview("overview"),
    NewEyebrows("new-eyebrows/{id}", "{id}");
}