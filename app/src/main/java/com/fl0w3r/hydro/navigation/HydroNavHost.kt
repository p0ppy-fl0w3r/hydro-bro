package com.fl0w3r.hydro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fl0w3r.core.hydro.ui.HydroScreen
import com.fl0w3r.core.hydro.ui.alarm.AlarmScreen
import com.fl0w3r.core.hydro.ui.overview.OverviewScreen
import com.fl0w3r.core.hydro.ui.profile.ProfileScreen
import com.fl0w3r.user.ui.login.LoginScreen


@Composable
fun HydroNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, startDestination = "login", modifier = modifier
    ) {
        composable("login") {
            LoginScreen(onLoginUser = {
                navController.navigate(HydroScreen.Overview.name) {
                    // Prevent navigating back to login screen if the user presses back button.
                    popUpTo(0)
                }

            })
        }

        composable(HydroScreen.Overview.name) {
            OverviewScreen()
        }
        composable(HydroScreen.Alarms.name) {
            AlarmScreen()
        }
        composable(HydroScreen.Profile.name) {
            ProfileScreen()
        }
    }
}