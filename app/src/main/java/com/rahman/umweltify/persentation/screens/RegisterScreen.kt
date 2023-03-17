package com.rahman.umweltify.persentation.screens



import android.annotation.SuppressLint
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.rahman.umweltify.persentation.util.*
import com.rahman.umweltify.persentation.viewModel.AuthViewModel
import com.rahman.umweltify.persentation.viewModel.RegisterState

@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter", "HardwareIds")
@Composable
fun RegisterScreen(nav: NavController = NavController(LocalContext.current), authVm:AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var fullname by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var doValidate by remember {
        mutableStateOf(false)
    }

    val isFormValid by derivedStateOf {
        password.isValidPassword() && email.isValidEmail()
    }


    val state by authVm.registerState.collectAsState()
    val snackbarRegisterHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarRegisterHostState) },
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
            Text(text = "Welcome!", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(15.dp))

//            CustomTextField(
//                value = fullname,
//                onChange = {
//                    fullname = it
//                },
//                placeHolder = {
//                    Text(text = "Full Name")
//                },
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Text,
//                )
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = email,
                doValidate = doValidate,
                errorText = email.validEmailText(),
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
                doValidate = doValidate,
                errorText = password.validPasswordText(),
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


            var context = LocalContext.current
            Spacer(modifier = Modifier.height(30.dp))

            state.let { state ->
                if (state is RegisterState.FAILURE) {
                    val message = state.message
                    LaunchedEffect(key1 = message) {
                        snackbarRegisterHostState.showSnackbar(
                            message,
                        )
                    }
                }
            }

            var deviceId =  Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            CustomButton(
                label = "Register",
                isLoading = state == RegisterState.LOADING,
            ) {
                doValidate = true;
                if(isFormValid){

                    var serialNumber = BatteryUtil.getDeviceId(context);
                    authVm.register(email, password,nav,deviceId,serialNumber);

                }}
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    nav.navigate(Routes.LoginScreen.name){
                        popUpTo(nav.graph.id)
                    }
                }) {
                    Text(text = "Sign In", color = MaterialTheme.colorScheme.scrim)
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


