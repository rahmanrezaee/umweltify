package com.rahman.umweltify.persentation.viewModel

import androidx.lifecycle.ViewModel
import com.rahman.umweltify.repository.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val repository: BatteryRepository
) : ViewModel() {

}