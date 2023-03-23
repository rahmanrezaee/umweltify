package com.rahman.umweltify.presentation.viewModel

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.rahman.umweltify.presentation.BaseApplication
import com.rahman.umweltify.presentation.constants.SharedConstant
import com.rahman.umweltify.repository.BatteryRepository
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
    var temperatureState: MutableState<TemperState> = mutableStateOf(TemperState.CNG)


    init {
        onBoard.value = preference.getBoolean(SharedConstant.onBoardStatue, false);
        getTheme()
        getTemp()
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


    private fun getTemp() {

        val temp: String? = preference.getString(SharedConstant.temperMode, "c");
        when (temp) {
            "c" -> {
                temperatureState.value = TemperState.CNG
            }
            "f" -> {
                temperatureState.value = TemperState.FRT
            }
            "k" -> {
                temperatureState.value = TemperState.KOL
            }
            else -> {
                temperatureState.value = TemperState.CNG
            }
        }
    }

    fun changeTemp(temp: TemperState) {
        temperatureState.value = temp;
        preference.edit {
            when (temp) {
                TemperState.FRT -> {
                    this.putString(SharedConstant.temperMode, "f")

                }
                TemperState.CNG -> {

                    this.putString(SharedConstant.temperMode, "c")
                }
                TemperState.KOL -> {
                    this.putString(SharedConstant.temperMode, "k")
                }
            }

            this.apply()
        }
    }

    fun convertTemp(degree: Double): String {

        var result: String = when (temperatureState.value) {
            TemperState.FRT -> {
                "${String.format("%.1f", ((degree * 9 / 5) + 32))} °F"
            }
            TemperState.CNG -> {
                "${String.format("%.1f", degree)} °C"
            }
            TemperState.KOL -> {
                "${String.format("%.1f", degree + 273.15)} °K"
            }
        }

        return result;

    }

    fun saveDefaultLocation(location: Location?) {

        val selectedAddress: String? = preference.getString(SharedConstant.addressName, "");
        Log.i(
            "AddressLat",
            "selected $selectedAddress ${location?.hasAltitude()} ${location?.altitude}"
        )

        if (location != null && (selectedAddress == null || selectedAddress.isEmpty())) {

            preference.edit {
                this.putFloat(SharedConstant.addressLat, location.latitude.toFloat())
                this.putFloat(SharedConstant.addressAlt, location.altitude.toFloat())
                this.putFloat(SharedConstant.addressLon, location.longitude.toFloat())
                this.apply()

            }
        }

    }
}


enum class ThemeState {
    DARK_MODE,
    LIGHT_MODE,
    SYSTEM
}

enum class TemperState {
    CNG,
    FRT,
    KOL
}