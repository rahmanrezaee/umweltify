package com.rahman.bettary_app.persentation.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.repository.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val repository: BatteryRepository
) : ViewModel() {

}