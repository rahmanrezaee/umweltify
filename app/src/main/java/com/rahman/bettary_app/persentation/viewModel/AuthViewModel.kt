package com.rahman.bettary_app.persentation.viewModel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahman.bettary_app.repository.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: BatteryRepository
) : ViewModel() {

    val isLoading = MutableLiveData(false);
    val isLogin = MutableLiveData(true);

}