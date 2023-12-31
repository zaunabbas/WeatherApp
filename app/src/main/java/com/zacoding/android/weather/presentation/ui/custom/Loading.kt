package com.zacoding.android.weather.presentation.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .then(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
    ) {
        CircularProgressIndicator()
    }
}
