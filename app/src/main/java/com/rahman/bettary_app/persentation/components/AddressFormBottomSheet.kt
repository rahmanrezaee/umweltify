package com.rahman.bettary_app.persentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(device = Devices.PIXEL_4_XL)
@Composable
fun AddressFormBottomSheet(user: String = "Bottel Nick", onCloseClick: () -> Unit = {}) {

    Column(
        Modifier
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 20.dp),
    ) {

        var locationName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
        }

        OutlinedTextField(
            value = locationName,
            onValueChange = { locationName = it },
            label = { Text("Location Name") }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            onClick = { onCloseClick.invoke() }) {
            Text(text = "SEARCH", color = Color.White)
        }

    }

}


