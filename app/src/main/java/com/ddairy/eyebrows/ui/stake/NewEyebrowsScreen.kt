package com.ddairy.eyebrows.ui.stake

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun NewEyebrowsBody(
    onClickReturnHome: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .semantics { contentDescription = "Home Screen" }
    ) {

    }
}