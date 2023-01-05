package com.fl0w3r.core.hydro.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OverviewScreen(modifier: Modifier = Modifier) {
    OverviewBody(modifier = modifier)
}

@Composable
fun OverviewBody(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("This is overview body.")
    }

}