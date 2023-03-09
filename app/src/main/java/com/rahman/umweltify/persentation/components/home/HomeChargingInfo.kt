package com.rahman.umweltify.persentation.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.viewModel.BatteryChargingViewModel

@Composable
fun HomeChargingInfo(batteryCharging: BatteryChargingViewModel) {


    Row(
        Modifier.height(125.dp)

    ) {

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 20.dp
            ),
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Text(text = "Charging Type", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "${batteryCharging.chargeState.value?.plugged()}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(
                        id = R.drawable.round_power
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(25.dp),
                )
            }
        }

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ), elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 20.dp
            ),

            shape = RoundedCornerShape(10),
            modifier = Modifier
                .padding(10.dp)

                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {

                Text(text = "Battery Type", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "${batteryCharging.chargeState.value?.technology}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(id = R.drawable.round_beenhere),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}