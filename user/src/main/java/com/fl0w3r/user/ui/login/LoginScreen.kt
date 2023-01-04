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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fl0w3r.core.ui.theme.HydroTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        DecorationBox()

        Text(text = "Hello There!", style = MaterialTheme.typography.h1)
        InputSection()

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
fun InputSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(value = "", onValueChange = {}, label = {
                Text(text = "Username")
            }, placeholder = {
                Text(text = "Enter your username...")
            })


            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Enter your password...")
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )


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
                onClick = { /*TODO*/ },

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
            LoginScreen()
        }
    }


}