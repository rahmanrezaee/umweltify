package com.rahman.bettary_app.persentation.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rahman.bettary_app.persentation.BaseApplication
import com.rahman.bettary_app.repository.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val application: BaseApplication,
    private val repository: BatteryRepository
) :ViewModel(){

    var loading: MutableState<Boolean>  = mutableStateOf(true)

    init {


        loading.value = true;
//        repository.getAddressList


    }


}