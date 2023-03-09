package com.rahman.umweltify.persentation.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.components.CustomTextField
import com.rahman.umweltify.persentation.routes.Routes
import com.rahman.umweltify.persentation.theme.BatteryTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPassword(nav: NavController = NavController(LocalContext.current)) {
    var email by remember {
        mutableStateOf("")
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(

                navigationIcon = {
                    IconButton(onClick = {
                        nav.popBackStack()
                    }) {
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

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    nav.navigate(Routes.ResetPasswordScreen.name)
                },
                enabled = email.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Send Reset Email")
            }
            Spacer(modifier = Modifier.weight(1f))
        }


    }
}


@Preview
@Composable
fun PreviewForgetPassword() {
    BatteryTheme(
        darkTheme = false
    ) {
        ForgetPassword()
    }
}