package com.fl0w3r.hydro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fl0w3r.core.hydro.ui.HydroScreen
import com.fl0w3r.core.hydro.ui.HydroTabRow
import com.fl0w3r.core.ui.theme.HydroTheme
import com.fl0w3r.hydro.navigation.HydroNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HydroApp()
        }
    }
}

@Composable
fun HydroApp() {

    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry.value?.destination?.route


    HydroTheme {
        Scaffold(
            bottomBar = {
                // Only show the bottom bar if you're not in login or sign up screens.
                if (currentRoute != "login" && currentRoute != "sign_up") {
                    val currentScreen = HydroScreen.fromRoute(currentRoute)
                    HydroTabRow(
                        allScreens = HydroScreen.values().toList(), onTabSelected = {
                            navController.navigate(it.name)

                        }, currentScreen = currentScreen
                    )
                }
            },
        ) { padding ->
            HydroNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
            )
        }
    }
}
