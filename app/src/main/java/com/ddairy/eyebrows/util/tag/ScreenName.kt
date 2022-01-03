package com.ddairy.eyebrows.util.tag

enum class ScreenName(
    val route: String,
    val argument: String = ""
) {
    Overview("overview"),
    NewName("new-eyebrows/{id}", "{id}");
}