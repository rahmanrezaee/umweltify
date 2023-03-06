package com.rahman.bettary_app.persentation.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rahman.bettary_app.R

@Composable
fun HomeChargingInfo() {


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

                Text(text = "Status", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "Charging",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painterResource(
                        id = R.drawable.charging
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.rotate(90F)
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

                Text(text = "Status", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "Charging",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }


    }
}
//
//    Column() {
//
//        Text(
//            text = if (batteryChargingVM.chargeState.value?.isCharging == true) "Charging" else "Dis Charged",
//            style = MaterialTheme.typography.headlineLarge,
//            modifier = Modifier.padding(vertical = 10.dp)
//        )
//        Card() {
//
//            Column(Modifier.padding(10.dp)) {
//
//                batteryChargingVM.chargeState.value?.level?.let {
//
//                    Row(
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 4.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "Battery Level"
//                        )
//                        Text(text = "$it %")
//                    }
//                    LinearProgressIndicator(
//                        progress = it/100.0f,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(10.dp)
//                            .padding(bottom = 10.dp)
//                    )
//                }
//
//
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 4.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = "Charge Current")
//                    Text(text = "${String.format("%.1f", batteryChargingVM.chargeState.value?.voltage?.times(batteryChargingVM.currentVoltage.value)?.div(1000000.0))} W / ${batteryChargingVM.currentVoltage.value} mA")
//                }
//                LinearProgressIndicator(
//                    progress = 0.4f,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(10.dp)
//                        .padding(bottom = 10.dp)
//                )
//
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 4.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "Voltage",
//                    )
//                    Text(text = "${batteryChargingVM.chargeState.value?.voltage} mV")
//                }
//                LinearProgressIndicator(
//                    progress = 0.4f,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(10.dp)
//                        .padding(bottom = 10.dp)
//                )
//
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 4.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "Temperature",
//                    )
//                    Text(text = "${batteryChargingVM.chargeState.value?.temperature?.div(10.0)} C")
//                }
//                batteryChargingVM.chargeState.value?.temperature?.div(1000.0)?.let {
//                    LinearProgressIndicator(
//                        progress = it.toFloat(),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(10.dp)
//                            .padding(bottom = 10.dp)
//                    )
//                }
//            }
//
//        }
//
//    }
//
//
//    if(batteryChargingVM.items.value.size >= 3){
//
//        var startItem = batteryChargingVM.items.value[2];
//        var endItem = batteryChargingVM.items.value[1];
//
//
//        Text(
//            text = "Last Charge Log",
//            style = MaterialTheme.typography.headlineSmall,
//            modifier = Modifier.padding(vertical = 10.dp)
//        )
//
//
//        Row(
//            Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Card(
//                Modifier
//                    .weight(1f)
//                    .padding(5.dp)
//            ) {
//                Column(
//                    Modifier
//                        .weight(1f)
//                        .padding(5.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "Total Used")
//                    Text("${(endItem.level!! - startItem.level!!)} %")
//                }
//            }
//            Card(
//                Modifier
//                    .weight(1f)
//                    .padding(5.dp)
//            ) {
//                Column(
//                    Modifier
//                        .weight(1f)
//                        .padding(5.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "Charge State")
//                    Text(if(endItem.isCharging!!) "Charging" else "Un Charge")
//                }
//            }
//
//            Card(
//                Modifier
//                    .weight(1f)
//                    .padding(5.dp)
//            ) {
//                Column(
//                    Modifier
//                        .weight(1f)
//                        .padding(5.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                ) {
//
//                    Log.i("ChargingLog","Charging Since ${TimeUtility.betweenTwoDate(startItem.startTime,endItem.startTime)} ${TimeUtility.convertLongToOnlyTime(endItem.startTime)} - ${ TimeUtility.convertLongToOnlyTime(startItem.startTime)}")
//                    Text(text = "Duration")
//
//                    val time = TimeUtility.betweenTwoDate(startItem.startTime,endItem.startTime);
//                    Text(time.ifEmpty { "Few Sec" })
//                }
//
//            }
//        }
//
//    }


//}
