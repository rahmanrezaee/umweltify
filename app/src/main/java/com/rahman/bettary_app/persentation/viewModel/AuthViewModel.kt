package com.rahman.bettary_app.persentation.viewModel


import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rahman.bettary_app.network.responses.LoginResponse
import com.rahman.bettary_app.persentation.constants.SharedConstant
import com.rahman.bettary_app.persentation.routes.Routes
import com.rahman.bettary_app.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val repository: AuthRepository,
    private val preferences: SharedPreferences
) :
    ViewModel() {
    val loginState = MutableStateFlow<LoginState>(LoginState.START)

    init {
        Log.i("loginState", "Init ${loginState.value}")
    }

    suspend fun checkIsLogin() {

        var token = preferences.getString(SharedConstant.token, "");


        Log.i("loginState", "before ${loginState.value}")

        if (token != null && token.isNotBlank())
            loginState.emit(LoginState.AUTHORIZED)
        else
            loginState.emit(LoginState.UNAUTHORIZED)

        Log.i("loginState", "after check ${loginState.value}")

    }

    fun login(email: String, password: String, nav: NavController) {
        viewModelScope.launch {
            loginState.emit(LoginState.LOADING)
            repository.login(email, password).onSuccess { data ->
                Log.i("ResultRegister", "Result accessToken ${data.accessToken}")
                loginState.emit(LoginState.AUTHORIZED)
                saveToken(data);
                nav.navigate(Routes.Dashboard.name) {
                    popUpTo(nav.graph.id)
                }
            }.onFailure {
                loginState.emit(LoginState.FAILURE(it.localizedMessage!!))
            }
        }
    }

    fun saveToken(userData: LoginResponse) {
        Log.i("TokenPreference", "token ${userData.accessToken}")
        preferences.edit {
            this.putString(SharedConstant.token, userData.accessToken)
            this.putString(SharedConstant.refreshToken, userData.refreshToken)
            this.apply()
        }
        val value = preferences.getString(SharedConstant.token, "");
        Log.i("TokenPreference", "token in save $value")
    }

    private fun removeToken() {

        preferences.edit {
            this.remove(SharedConstant.token)
            this.remove(SharedConstant.refreshToken)
            this.apply()
        }
    }

    fun logout(nav: NavController) {
        viewModelScope.launch {
            loginState.emit(LoginState.LOADING)
            removeToken();
            loginState.emit(LoginState.UNAUTHORIZED)
            nav.navigate(Routes.Dashboard.name) {
                popUpTo(nav.graph.id)
            }
        }
    }
}


sealed class LoginState {
    object START : LoginState()
    object UNAUTHORIZED : LoginState()
    object LOADING : LoginState()
    object AUTHORIZED : LoginState()
    data class FAILURE(val message: String) : LoginState()
}
