package com.rahman.bettary_app.persentation.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.viewModel.BatteryChargingViewModel

@Composable
fun HomeChargingInfoCharging(batteryCharging: BatteryChargingViewModel) {


    Row(
        Modifier.height(125.dp)

    ) {

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,),
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

                Text(text = "Charge Current", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "${String.format("%.1f",
                        batteryCharging.batteryInfo.value?.currentAmpere?.let {
                            batteryCharging.chargeState.value?.voltage?.times(
                                it
                            )?.div(1000000.0)
                        })} W / ${batteryCharging.batteryInfo.value?.currentAmpere} mA",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(id = R.drawable.round_autorenew),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(25.dp)
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

                Text(text = "Charge Average", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "${String.format("%.1f",
                        batteryCharging.batteryInfo.value?.averageAmpere?.let {
                            batteryCharging.chargeState.value?.voltage?.times(
                                it
                            )?.div(1000000.0)
                        })} W / ${batteryCharging.batteryInfo.value?.averageAmpere} mA",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(id = R.drawable.round_autorenew),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(25.dp)
                )
            }
        }


    }
}