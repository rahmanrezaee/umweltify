package com.rahman.bettary_app.persentation.components


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@SuppressLint("UnrememberedMutableState")
@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun AddressFormBottomSheet(user: String = "Bottel Nick", onCloseClick: () -> Unit = {}) {


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

            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            androidx.compose.material3.Text(text = "Save")
        }

    }

}


