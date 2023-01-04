package com.fl0w3r.hydro.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * A [MaterialTheme] for Hydro Bro.
 */
@Composable
fun HydroTheme(content: @Composable () -> Unit) {

    MaterialTheme(colors = ColorPalette, typography = Typography, content = content)
}
