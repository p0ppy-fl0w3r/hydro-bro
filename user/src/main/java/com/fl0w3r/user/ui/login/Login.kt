package com.fl0w3r.user.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fl0w3r.core.ui.theme.HydroTheme
import com.fl0w3r.model.LoginModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onLoginUser: (String) -> Unit
) {

    val tokenState by viewModel.tokenState.observeAsState(null)
    var errorMessage by remember {
        mutableStateOf("")
    }

    tokenState?.let {
        if (it.isValid) {
            LaunchedEffect(tokenState) {
                onLoginUser(it.token)
            }
        } else {
            errorMessage = it.errorMessage
        }
    }

    LoginBody(modifier = modifier, onLoginClick = {
        viewModel.authenticateUser(it)
    }, errorMessage = errorMessage)
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    onLoginClick: (LoginModel) -> Unit,
    errorMessage: String
) {
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        DecorationBox()

        Text(text = "Hello There!", style = MaterialTheme.typography.h1)
        InputSection(onLoginClick = {
            onLoginClick(it)
        }, errorMessage = errorMessage)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            DecorationBox(isEnd = true)
        }


    }
}

@Composable
fun DecorationBox(modifier: Modifier = Modifier, isEnd: Boolean = false) {
    Box(
        modifier = modifier
            .offset(
                y = if (isEnd) 30.dp else (-30).dp, x = 50.dp
            )
            .rotate(if (isEnd) 180 - 15f else -15f)
            .size(
                width = 200.dp, height = 150.dp
            )
            .clip(
                RectangleShape
            )
            .background(MaterialTheme.colors.primary)
    )
}

@Composable
fun InputSection(
    modifier: Modifier = Modifier, onLoginClick: (LoginModel) -> Unit, errorMessage: String
) {

    var loginModelState by remember {
        mutableStateOf(LoginModel())
    }

    Column(modifier = modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(value = loginModelState.username ?: "", onValueChange = {
                loginModelState = loginModelState.copy(
                    username = it
                )
            }, label = {
                Text(text = "Username")
            }, placeholder = {
                Text(text = "Enter your username...")
            })


            OutlinedTextField(
                value = loginModelState.password ?: "",
                onValueChange = {
                    loginModelState = loginModelState.copy(
                        password = it
                    )
                },
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Enter your password...")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic
                )
            }


            Row(
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Forgot your password?")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier
                .padding(8.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Sign Up", textDecoration = TextDecoration.Underline)
            }

            Button(
                onClick = {
                    onLoginClick(loginModelState)
                },

                ) {
                Text(
                    text = "Login", modifier = Modifier.padding(horizontal = 64.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {

    HydroTheme {
        Surface() {
            LoginBody(onLoginClick = {}, errorMessage = "Apples")
        }
    }


}