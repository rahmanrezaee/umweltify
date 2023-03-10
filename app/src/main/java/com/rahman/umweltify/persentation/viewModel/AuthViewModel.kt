package com.rahman.umweltify.persentation.viewModel


import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rahman.umweltify.network.CustomHttpException
import com.rahman.umweltify.persentation.constants.SharedConstant
import com.rahman.umweltify.persentation.routes.Routes
import com.rahman.umweltify.repository.AuthRepository
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
    val registerState = MutableStateFlow<RegisterState>(RegisterState.START)

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
                Log.i("ResultRegister", "Result accessToken ${data.data}")
                loginState.emit(LoginState.AUTHORIZED)
                saveToken(data.data);
                nav.navigate(Routes.Dashboard.name) {
                    popUpTo(nav.graph.id)
                }
            }.onFailure {
                if (it is CustomHttpException) {
                    loginState.emit(LoginState.FAILURE(it.body.getString("Message")))
                } else {
                    loginState.emit(LoginState.FAILURE(it.localizedMessage!!))
                }
            }
        }
    }

    fun saveToken(token: String) {
        Log.i("TokenPreference", "token ${token}")
        preferences.edit {
            this.putString(SharedConstant.token, token)
//            this.putString(SharedConstant.refreshToken, token)
            this.apply()
        }
        val value = preferences.getString(SharedConstant.token, "");
        Log.i("TokenPreference", "token in save $value")
    }

    private fun removeToken() {

        preferences.edit {
            this.remove(SharedConstant.token)
//            this.remove(SharedConstant.refreshToken)
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

    fun register(email: String, password: String, nav: NavController) {

        viewModelScope.launch {
            registerState.emit(RegisterState.LOADING)
            repository.register(email, password).onSuccess { data ->
                Log.i("ResultRegister", "Result accessToken ${data}")
                saveToken(data.data);
                loginState.emit(LoginState.AUTHORIZED)
                registerState.emit(RegisterState.COMPLETE)
                nav.navigate(Routes.Dashboard.name) {
                    popUpTo(nav.graph.id)
                }
            }.onFailure {
                if (it is CustomHttpException) {

                    registerState.emit(RegisterState.FAILURE(it.body.getString("Message")))
                    Log.i("ResultRegister", "error ${it.body.getString("Message")}")
                } else {
                    registerState.emit(RegisterState.FAILURE(it.localizedMessage!!))

                }
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


sealed class RegisterState {
    object START : RegisterState()
    object LOADING : RegisterState()
    object COMPLETE : RegisterState()
    data class FAILURE(val message: String) : RegisterState()
}
