package com.ddairy.eyebrows.ui.admob

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.ui.components.EyebrowText
import com.ddairy.eyebrows.ui.theme.EyebrowsTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    val isInEditMode = LocalInspectionMode.current
    Divider(modifier = Modifier.padding(vertical = 8.dp))
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isInEditMode) {
            EyebrowText(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 6.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.ads_text),
            )
        } else {
            AndroidView(
                modifier = modifier.fillMaxWidth(),
                factory = { context ->
                    AdView(context).apply {
                        adSize = AdSize.BANNER
                        adUnitId = context.resources.getString(R.string.admob_banner_id)
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )
        }
    }
    Divider(modifier = Modifier.padding(vertical = 8.dp))
}

@Preview(showBackground = true)
@Composable
fun AdvertPreview() {
    EyebrowsTheme {
        AdvertView()
    }
}