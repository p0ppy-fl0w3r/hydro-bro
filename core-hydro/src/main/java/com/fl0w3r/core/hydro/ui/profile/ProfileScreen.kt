package com.fl0w3r.core.hydro.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fl0w3r.core.ui.theme.HydroTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onLogout: () -> Unit) {
    ProfileBody(modifier = modifier, onLogout = { onLogout() })
}

@Composable
fun ProfileBody(modifier: Modifier = Modifier, onLogout: () -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { onLogout() }, modifier = Modifier.padding(end = 8.dp)) {
                Row() {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(text = "Logout")
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileBodyView() {
    HydroTheme {
        Surface() {
            ProfileBody(onLogout = {})
        }
    }

}