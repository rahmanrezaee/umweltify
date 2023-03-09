package com.rahman.umweltify.persentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    label: String,
    modifier: Modifier = Modifier,
   isLoading:Boolean = false,
    enable:Boolean = true,
    onClick:()->Unit
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        enabled = enable,
        modifier = Modifier.fillMaxWidth().then(modifier),
        shape = RoundedCornerShape(16.dp)
    ) {
        if (!isLoading) {
            Text(text = label)
        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(20.dp)
            )
        }
    }

}