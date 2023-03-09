@file:OptIn(ExperimentalPermissionsApi::class)

package com.rahman.umweltify.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.routes.Routes
import com.rahman.umweltify.persentation.viewModel.AuthViewModel
import com.rahman.umweltify.persentation.viewModel.LoginState
import com.rahman.umweltify.persentation.viewModel.SetupViewModel


@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun SplashScreen(nav: NavController = NavController(LocalContext.current,),authViewModel: AuthViewModel,setupViewModel: SetupViewModel ) {

    var context = LocalContext.current;


    LaunchedEffect(key1 = true) {
        authViewModel.checkIsLogin()
        setupViewModel.onBoard.value.let {
            if (it == true) {
                if (authViewModel.loginState.value == LoginState.AUTHORIZED) {
                    nav.navigate(Routes.Dashboard.name) {
                        popUpTo(nav.graph.id)
                    }
                } else {
                    nav.navigate(Routes.LoginScreen.name) {
                        popUpTo(nav.graph.id)
                    }
                }

            } else {
                nav.navigate(Routes.OnBoardScreen.name) {
                    popUpTo(nav.graph.id)
                }
            }
        }
    }
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .width(100.dp)
        )
    }


}

