package com.fl0w3r.hydro

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.fl0w3r.user.ui.login.Login
import com.fl0w3r.core.ui.theme.HydroTheme
import com.fl0w3r.hydro.datastore.HydroPreferences
import com.fl0w3r.user.ui.login.LoginScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val hydroPreferences = HydroPreferences(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            HydroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginScreen(onLoginUser = {
                        CoroutineScope(Dispatchers.IO).launch {
                            // Save the token in a datastore so that the user won't have to enter their
                            // credentials everytime they open their app.
                            hydroPreferences.updateToken(it)
                        }
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    })
                }
            }
        }
    }
}
