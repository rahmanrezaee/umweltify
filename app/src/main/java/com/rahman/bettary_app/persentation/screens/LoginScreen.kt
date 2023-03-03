package com.rahman.bettary_app.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.components.CustomTextField
import com.rahman.bettary_app.persentation.routes.Routes
import com.rahman.bettary_app.persentation.theme.BatteryTheme


@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(nav: NavController = NavController(LocalContext.current)) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val isFormValid by derivedStateOf {
        email.isNotBlank() && password.length >= 2
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nav.navigate(Routes.Dashboard.name){
                        popUpTo(nav.graph.id)
                    }
                },
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            ) {
                Text(
                    "Skip",
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .weight(1f)
                        .size(100.dp),
                )


            Text(text = "Welcome Back!", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = email,
                onChange = {
                    email = it
                },
                placeHolder = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = password,
                onChange = {
                    password = it
                },
                placeHolder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painterResource(id = if (isPasswordVisible) R.drawable.round_visibility else R.drawable.round_visibility_off),
                            contentDescription = "Password Toggle"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    nav.navigate(Routes.Dashboard.name){
                        popUpTo(nav.graph.id)
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Log In")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    nav.navigate(Routes.RegisterScreen.name){
                        popUpTo(nav.graph.id)
                    }
                }) {
                    Text(text = "Sign Up", color = MaterialTheme.colorScheme.scrim)
                }
                TextButton(onClick = {
                    nav.navigate(Routes.ForgetPasswordScreen.name)
                }) {
                    Text(text = "Forgot Password?", color = MaterialTheme.colorScheme.scrim)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }


    }
}


@Preview
@Composable
fun PreviewLoginScreen() {
    BatteryTheme(
        darkTheme = false
    ) {
        LoginScreen()
    }
}