package com.fl0w3r.core.hydro.ui.profile

import androidx.compose.foundation.Image
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BedroomBaby
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fl0w3r.core.hydro.R
import com.fl0w3r.core.hydro.ui.profile.state.ProfileUiState
import com.fl0w3r.core.ui.theme.HydroTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val profileUiState by profileViewModel.profileUiState.observeAsState(null)

    if (profileUiState == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        ProfileBody(modifier = modifier, onLogout = {
            runBlocking {
                // Make sure that the token is cleared from the datastore before doing other logout logic.
                profileViewModel.clearToken()
                onLogout()
            }
        }, profileUiState = profileUiState!!)
    }
}

@Composable
fun ProfileBody(
    modifier: Modifier = Modifier, onLogout: () -> Unit, profileUiState: ProfileUiState
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProfileHeader(onLogout = { onLogout() })
        UserInfo(profileUiState = profileUiState)
    }
}

@Composable
fun UserInfo(profileUiState: ProfileUiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = profileUiState.username,
            style = MaterialTheme.typography.h1.copy(fontSize = 44.sp)
        )
        Text(text = profileUiState.email, style = MaterialTheme.typography.subtitle1)

        LabeledText(
            icon = Icons.Default.Man,
            label = "Full Name",
            text = "${profileUiState.firstName} ${profileUiState.lastName}"
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledText(
            icon = Icons.Default.Email,
            label = "Email",
            text = profileUiState.email,
            tint = Color(0xFFFFA500),
            iconPadding = 4.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledText(
            icon = Icons.Default.ChildCare,
            label = "Age",
            text = profileUiState.age.toString(),
            tint = Color(0xFF1EB980),
            iconPadding = 4.dp
        )

    }
}

@Composable
fun ProfileHeader(modifier: Modifier = Modifier, onLogout: () -> Unit) {
    Surface(modifier = modifier) {

        Box(
            modifier = Modifier
                .offset(y = -(50).dp)
                .fillMaxWidth()
                .height(300.dp)
                .background(color = MaterialTheme.colors.primary),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Image(
                    painter = painterResource(id = R.drawable.cat),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(y = 50.dp)
                        .size(100.dp)
                        .background(color = Color.Red)
                )
            }
        }

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

@Composable
fun LabeledText(
    icon: ImageVector,
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.primary,
    iconPadding: Dp = 0.dp,
) {
    Column(modifier = modifier) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.padding(start = iconPadding)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = label, style = MaterialTheme.typography.subtitle1)
        }

        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        Divider(thickness = 2.dp, modifier = Modifier.background(color = tint))
    }
}

@Preview
@Composable
fun LabeledTextPreview() {
    HydroTheme {
        Surface {
            LabeledText(
                label = "Full Name", text = "Mittens Claw", icon = Icons.Default.Man
            )
        }
    }
}

@Preview
@Composable
fun ProfileBodyView() {
    HydroTheme {
        Surface() {
            ProfileBody(
                onLogout = {}, profileUiState = ProfileUiState(
                    username = "Cat1234",
                    firstName = "Mittens",
                    lastName = "Claws",
                    age = 21,
                    email = "cat@gmail.com"
                )
            )
        }
    }

}