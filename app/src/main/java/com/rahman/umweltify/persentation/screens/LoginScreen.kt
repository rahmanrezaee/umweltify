package com.rahman.umweltify.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.components.CustomButton
import com.rahman.umweltify.persentation.components.CustomTextField
import com.rahman.umweltify.persentation.routes.Routes
import com.rahman.umweltify.persentation.viewModel.AuthViewModel
import com.rahman.umweltify.persentation.viewModel.LoginState


@SuppressLint(
    "UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun LoginScreen(
    nav: NavController = NavController(LocalContext.current),
    authViewModel: AuthViewModel
) {


    val state by authViewModel.loginState.collectAsState()
    val snackbarLoginHostState = remember { SnackbarHostState() }


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
        snackbarHost = { SnackbarHost(snackbarLoginHostState) },
    ) {

        Box(
            contentAlignment = Alignment.BottomEnd,
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


                state.let { state ->
                    if (state is LoginState.FAILURE) {
                        val message = state.message
                        LaunchedEffect(key1 = message) {
                            snackbarLoginHostState.showSnackbar(
                                message,
                            )
                        }
                    }
                }

                CustomButton(
                    label = "Log In",
                    isLoading = state == LoginState.LOADING,
                    enable = isFormValid
                ) {
                    authViewModel.login(email, password, nav);
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = {
                        nav.navigate(Routes.RegisterScreen.name) {
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

            Row(
                Modifier.padding(bottom = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        authViewModel.loginState.value = LoginState.UNAUTHORIZED;
                        nav.navigate(Routes.Dashboard.name) {
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
        }


    }
}

