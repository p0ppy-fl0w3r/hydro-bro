package com.fl0w3r.user.ui.signup

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fl0w3r.core.ui.theme.HydroTheme
import com.fl0w3r.user.ui.signup.state.ApiStatus
import com.fl0w3r.user.ui.signup.state.SignupApiState
import com.fl0w3r.user.ui.signup.state.SignupErrorState
import com.fl0w3r.user.ui.signup.state.SignupState

@Composable
fun SignupScreen(
    onSignupComplete: () -> Unit,
    modifier: Modifier = Modifier,
    signupViewModel: SignupViewModel = viewModel()
) {

    val signupState by signupViewModel.signupState.observeAsState(initial = SignupState())
    val signupErrorState by signupViewModel.signupErrorState.observeAsState(initial = SignupErrorState())
    val signupApiState by signupViewModel.signupApiState.observeAsState(
        initial = SignupApiState(
            status = ApiStatus.INITIAL,
            message = ""
        )
    )

    val context = LocalContext.current

    if (signupApiState.status == ApiStatus.SUCCESS) {
        LaunchedEffect(signupApiState) {
            Toast.makeText(context, signupApiState.message, Toast.LENGTH_SHORT).show()
            onSignupComplete()
        }
    }
    if (signupApiState.status == ApiStatus.FAILED) {
        LaunchedEffect(signupApiState) {
            Toast.makeText(context, signupApiState.message, Toast.LENGTH_SHORT).show()
            signupViewModel.resetApiState()
        }
    }

    SignupBody(
        modifier = modifier,
        signupState = signupState,
        signupErrorState = signupErrorState,
        onSignupClicked = {
            signupViewModel.onSignUp(it)
        },
        onStateChange = {
            signupViewModel.onStateChange(it)
        },
        isPending = signupApiState.status == ApiStatus.PENDING
    )
}

@Composable
fun SignupBody(
    signupState: SignupState,
    signupErrorState: SignupErrorState,
    onStateChange: (SignupState) -> Unit,
    onSignupClicked: (SignupState) -> Unit,
    isPending: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            DecorationBox()

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Register!", style = MaterialTheme.typography.h1.copy(fontSize = 54.sp))
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        SignupForm(
            onSignupClicked = {
                onSignupClicked(it)
            }, signupState = signupState,
            signupErrorState = signupErrorState,
            onStateChange = {
                onStateChange(it)
            }, isPending = isPending,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )

        Spacer(modifier = Modifier.height(20.dp))



        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            DecorationBox(isEnd = true)
        }
    }
}

@Composable
fun SignupForm(
    signupState: SignupState,
    signupErrorState: SignupErrorState,
    onStateChange: (SignupState) -> Unit,
    onSignupClicked: (SignupState) -> Unit,
    isPending: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        InputField(
            value = signupState.username,
            onValueChange = {
                onStateChange(
                    signupState.copy(
                        username = it
                    )
                )
            },
            label = "Username",
            placeholder = "Enter a username...",
            error = signupErrorState.usernameError
        )
        InputField(
            value = signupState.firstName,
            onValueChange = {
                onStateChange(
                    signupState.copy(
                        firstName = it
                    )
                )
            },
            label = "First Name",
            placeholder = "Enter your first name...",
            error = signupErrorState.firstNameError
        )

        InputField(
            value = signupState.lastName,
            onValueChange = {
                onStateChange(
                    signupState.copy(
                        lastName = it
                    )
                )
            },
            label = "Last Name",
            placeholder = "Enter your last name...",
            error = signupErrorState.lastNameError
        )
        InputField(
            value = signupState.email,
            onValueChange = {
                onStateChange(
                    signupState.copy(
                        email = it
                    )
                )
            },
            label = "Email",
            placeholder = "Enter your email...",
            error = signupErrorState.emailError,
            keyboardType = KeyboardType.Email
        )
        InputField(
            value = signupState.age?.toString() ?: "",
            onValueChange = {
                onStateChange(
                    signupState.copy(
                        age = it.toIntOrNull()
                    )
                )
            },
            label = "Age",
            placeholder = "Enter your age...",
            error = signupErrorState.ageError,
            keyboardType = KeyboardType.Number
        )

        InputField(
            value = signupState.password,
            onValueChange = {
                onStateChange(
                    signupState.copy(
                        password = it
                    )
                )
            },
            label = "Password",
            placeholder = "Enter a password...",
            error = signupErrorState.passwordError,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

        SignupButton(onSignupClicked = {
            onSignupClicked(signupState)
        }, isPending = isPending)

    }
}

@Composable
private fun SignupButton(
    modifier: Modifier = Modifier,
    onSignupClicked: () -> Unit,
    isPending: Boolean
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = { onSignupClicked() }, shape = RectangleShape
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Sign Up!")
                if (isPending) {
                    LinearProgressIndicator(
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.width(190.dp)
                    )
                }
            }

        }
    }
}

@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    error: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        OutlinedTextField(value = value, onValueChange = {
            onValueChange(it)
        }, label = {
            Text(text = label)
        }, placeholder = {
            Text(text = placeholder)
        }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
            visualTransformation = visualTransformation
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.error,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun DecorationBox(modifier: Modifier = Modifier, isEnd: Boolean = false) {
    Box(
        modifier = modifier
            .offset(
                y = if (isEnd) (0).dp else 40.dp
            )
            .size(
                width = 230.dp, height = 100.dp
            )
            .clip(
                if (isEnd) {
                    CutCornerShape(bottomStart = 100.dp)
                } else {
                    CutCornerShape(topEnd = 100.dp)
                }
            )
            .background(MaterialTheme.colors.primary)
    )
}

@Preview
@Composable
private fun SignUpPreview() {

    val state = SignupState(
        username = "Test",
        firstName = "Test",
        lastName = "Person",
        email = "test@gmail.com",
        password = "Apple",
        age = 16
    )

    val errorState = SignupErrorState(
        usernameError = "",
        firstNameError = "",
        lastNameError = "",
        emailError = "",
        passwordError = "",
        ageError = "",
    )

    HydroTheme {
        Surface {
            SignupBody(
                signupErrorState = errorState,
                signupState = state,
                onStateChange = {},
                onSignupClicked = {},
                isPending = true
            )
        }
    }
}