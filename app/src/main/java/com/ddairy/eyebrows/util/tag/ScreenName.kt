package com.ddairy.eyebrows.util.tag

enum class ScreenName(
    val route: String,
    val argument: String = ""
) {
    Welcome("welcome"),
    Home("home"),
    Eyebrow("eyebrow/{id}", "{id}");
}