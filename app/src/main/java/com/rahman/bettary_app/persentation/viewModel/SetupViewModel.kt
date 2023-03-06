package com.rahman.bettary_app.persentation.viewModel

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.rahman.bettary_app.persentation.BaseApplication
import com.rahman.bettary_app.persentation.constants.SharedConstant
import com.rahman.bettary_app.repository.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val preference: SharedPreferences,
    private val repository: BatteryRepository,
    private val context: BaseApplication
) : ViewModel() {


    var onBoard: MutableState<Boolean?> = mutableStateOf(null)

    var themeState: MutableState<ThemeState> = mutableStateOf(ThemeState.LIGHT_MODE)


    init {
        onBoard.value = preference.getBoolean(SharedConstant.onBoardStatue, false);
        getTheme()
    }

    suspend fun disableOnBoard() {
        val editor: Editor = preference.edit()
        editor.putBoolean(SharedConstant.onBoardStatue, true)
        editor.apply()
    }

    private fun getTheme() {

        val theme: String? = preference.getString(SharedConstant.themeMode, "dark");
        when (theme) {
            "Dark" -> {
                themeState.value = ThemeState.DARK_MODE
            }
            "Light" -> {
                themeState.value = ThemeState.LIGHT_MODE
            }
            "System" -> {
                themeState.value = ThemeState.SYSTEM
            }
            else -> {
                themeState.value = ThemeState.LIGHT_MODE
            }
        }
    }

    fun changeTheme(mode: ThemeState) {
        themeState.value = mode;
        preference.edit {
            when (mode) {
                ThemeState.LIGHT_MODE -> {

                    this.putString(SharedConstant.themeMode, "Light")

                }
                ThemeState.SYSTEM -> {

                    this.putString(SharedConstant.themeMode, "System")
                }
                ThemeState.DARK_MODE -> {
                    this.putString(SharedConstant.themeMode, "Dark")
                }
            }

            this.apply()
        }
    }
}


enum class ThemeState {
    DARK_MODE,
    LIGHT_MODE,
    SYSTEM
}