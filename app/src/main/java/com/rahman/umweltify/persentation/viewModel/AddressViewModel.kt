package com.rahman.umweltify.persentation.viewModel

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahman.umweltify.db.entity.AddressED
import com.rahman.umweltify.persentation.BaseApplication
import com.rahman.umweltify.persentation.constants.SharedConstant
import com.rahman.umweltify.persentation.util.RequestState
import com.rahman.umweltify.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val application: BaseApplication,
    private val preferences: SharedPreferences,
    private val repository: AddressRepository
) : ViewModel() {


    private val _items =
        MutableStateFlow<RequestState<List<AddressED>>>(RequestState.Idle)



    val items: StateFlow<RequestState<List<AddressED>>> = _items

    var selectedAddress: MutableState<String?> = mutableStateOf(null);

    init {
        loadAddress();
    }

    private fun loadAddress() {


        selectedAddress.value = preferences.getString(SharedConstant.addressName, null);

        _items.value = RequestState.Loading
        try {
            viewModelScope.launch {
                // Trigger the flow and consume its elements using collect
                repository.getAll().collect {
                    _items.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _items.value = RequestState.Error(e)
        }

    }

     fun insertAddress(address: AddressED) {
         viewModelScope.launch(Dispatchers.IO) {
             repository.insertOne(address)
         }
    }
    fun selectAddress(address: AddressED) {
        preferences.edit {
            this.putString(SharedConstant.addressName, address.placeName)
            this.putFloat(SharedConstant.addressLat, address.latitude.toFloat())
            this.putFloat(SharedConstant.addressLon, address.longitude.toFloat())
            this.apply()
        }
        selectedAddress.value = address.placeName
    }

    fun deleteAddress(item: AddressED) {
        // check getNexValue
        viewModelScope.launch(Dispatchers.IO) {
            if(item.placeName == selectedAddress.value){
                selectedAddress.value = null
            }
            repository.deleteOne(item)
        }
    }

}