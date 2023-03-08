package com.rahman.bettary_app.persentation.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.util.TimeUtility
import com.rahman.bettary_app.persentation.viewModel.BatteryChargingViewModel


@Composable
fun LastChargeTime(
    batteryCharging: BatteryChargingViewModel
) {

    if(batteryCharging.historyBatteryItems.value.size >= 3) {

        var startItem = batteryCharging.historyBatteryItems.value[2];
        var endItem = batteryCharging.historyBatteryItems.value[1];


        Box(
            contentAlignment = Alignment.CenterEnd,
            propagateMinConstraints = true
        ) {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 20.dp
                ),
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentWidth()
//                   .height(125.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    Arrangement.End,
                    Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {


                        Text(
                            text = "Last Charging State",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = if (endItem.isCharging!!) "Charging" else "Un Charge",
//                    text = "${batteryCharging.chargeState.value?.plugged()}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Last Charging Time",
                            style = MaterialTheme.typography.labelSmall
                        )
                        val time =
                            TimeUtility.betweenTwoDate(startItem.startTime, endItem.startTime);

                        Text(
                            text = time.ifEmpty { "Few Sec" },
//                    text = "${batteryCharging.chargeState.value?.plugged()}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Total Charging Level",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "${(endItem.level!! - startItem.level!!)} %",
//                    text = "${batteryCharging.chargeState.value?.plugged()}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                }

            }

            Icon(
                painterResource(
                    id = R.drawable.round_av_timer
                ),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                modifier = Modifier
                    .size(150.dp)

            )
        }

    }
}

