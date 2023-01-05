package com.fl0w3r.core.hydro.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    ProfileBody(modifier = modifier)
}

@Composable
fun ProfileBody(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("This is profile screen.")
    }

}