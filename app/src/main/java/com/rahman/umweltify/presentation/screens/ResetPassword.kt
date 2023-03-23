@file:OptIn(ExperimentalMaterial3Api::class)

package com.rahman.umweltify.presentation.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rahman.umweltify.R
import com.rahman.umweltify.presentation.components.CustomTextField
import com.rahman.umweltify.presentation.routes.Routes
import com.rahman.umweltify.presentation.theme.BatteryTheme


@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResetPasswordScreen(nav: NavController = NavController(LocalContext.current)) {

    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    val isFormValid by derivedStateOf {
        password.isNotBlank() && password.length >= 2 && confirmPassword.isNotBlank() && confirmPassword.length >= 2
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = {}
            )
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

            Spacer(modifier = Modifier.height(15.dp))
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

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = confirmPassword,
                onChange = {
                    confirmPassword = it
                },
                placeHolder = {
                    Text(text = "Confirm Password")
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
                    nav.navigate(Routes.LoginScreen.name){
                        popUpTo(nav.graph.id)
                    }
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Reset Password")
            }
          
            Spacer(modifier = Modifier.weight(1f))
        }


    }
}


@Preview
@Composable
fun PreviewResetPasswordScreen() {
    BatteryTheme(
        darkTheme = false
    ) {
        ResetPasswordScreen()
    }
}