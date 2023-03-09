package com.rahman.umweltify.persentation.components


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rahman.umweltify.db.entity.AddressED
import com.rahman.umweltify.persentation.util.findActivity
import com.rahman.umweltify.persentation.viewModel.AddressViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun AddressFormBottomSheet(addressVM: AddressViewModel, onCloseClick: () -> Unit = {}) {

    var context = LocalContext.current

     var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context.findActivity())

    Column(
        Modifier
            .padding(vertical = 20.dp, horizontal = 20.dp),
        Arrangement.SpaceBetween,
    ) {

        var name by remember {
            mutableStateOf("")
        }

        val isFormValid by derivedStateOf {
            name.isNotBlank()
        }
        CustomTextField(
            value = name,
            onChange = {
                name = it
            },
            placeHolder = {
                Text(text = "Address Name")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        androidx.compose.material3.Button(
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->

                        addressVM.insertAddress(
                            AddressED(
                                placeName = name,
                                longitude = location?.longitude?: 0.0,
                                latitude = location?.latitude?: 0.0
                            )
                        )
                        name = ""
                        onCloseClick.invoke()

                    }.addOnFailureListener {

                        it.printStackTrace()
                        Toast.makeText(context,"Error To Get Current Location",Toast.LENGTH_SHORT).show()
                    }


            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            androidx.compose.material3.Text(text = "Save")
        }

    }

}


