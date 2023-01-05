package com.fl0w3r.core.hydro.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AlarmScreen(modifier: Modifier = Modifier) {
    AlarmBody(modifier = modifier)
}

@Composable
fun AlarmBody(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("This is alarm screen.")
    }

}