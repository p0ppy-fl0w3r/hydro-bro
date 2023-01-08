package com.fl0w3r.core.hydro.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector

enum class HydroScreen(
    val icon: ImageVector,
) {
    Overview(
        icon = Icons.Filled.PieChart,
    ),
    Alarms(
        icon = Icons.Filled.Alarm,
    ),
    Profile(
        icon = Icons.Filled.Man,
    );

    companion object {

        /**
         * Returns a [HydroScreen] from the provided route string.
        * */
        fun fromRoute(route: String?): HydroScreen =
            when (route?.substringBefore("/")) {
                Alarms.name -> Alarms
                Profile.name -> Profile
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("$route not found!")
            }
    }
}
