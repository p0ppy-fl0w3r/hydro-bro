package com.fl0w3r.hydro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fl0w3r.core.hydro.ui.HydroScreen
import com.fl0w3r.core.hydro.ui.alarm.AlarmScreen
import com.fl0w3r.core.hydro.ui.alarm.info.AlarmInfoScreen
import com.fl0w3r.core.hydro.ui.overview.OverviewScreen
import com.fl0w3r.core.hydro.ui.profile.ProfileScreen
import com.fl0w3r.user.ui.login.LoginScreen
import com.fl0w3r.user.ui.signup.SignupScreen


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
            },
                onSignupClicked = {
                    navController.navigate("sign_up")
                })
        }
        composable("sign_up") {
            SignupScreen(onSignupComplete = {
                navController.navigate("login")
            })
        }

        composable(HydroScreen.Overview.name) {
            OverviewScreen()
        }
        composable(HydroScreen.Alarms.name) {
            AlarmScreen(onAlarmClicked = {
                navigateToAlarmInfo(navController, it)
            })
        }
        composable(
            route = "${HydroScreen.Alarms.name}/{alarmId}",
            arguments = listOf(navArgument(name = "alarmId") {
                type = NavType.IntType
            })
        ) { entry ->

            val alarmId = entry.arguments?.getInt("alarmId")
            alarmId?.let {
                AlarmInfoScreen(alarmId = it, onCompleteUpdate = {
                    navController.navigate(HydroScreen.Alarms.name)
                })
            }
        }
        composable(HydroScreen.Profile.name) {
            ProfileScreen(onLogout = {

                navController.navigate("login") {
                    popUpTo(0)
                }

            })
        }
    }
}

private fun navigateToAlarmInfo(navController: NavHostController, alarmId: Int) {
    navController.navigate("${HydroScreen.Alarms}/$alarmId")
}
