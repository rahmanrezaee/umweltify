package com.rahman.bettary_app.persentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.persentation.util.TimeUtility
import com.rahman.bettary_app.persentation.viewModel.BatteryChargingViewModel
import kotlin.math.abs

@Composable
fun HomeChargingInfo() {

    var batteryChargingVM = hiltViewModel<BatteryChargingViewModel>()

    Column() {

        Text(
            text = if (batteryChargingVM.isCharging.value) "Charging" else "Dis Charged",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Card() {

            Column(Modifier.padding(10.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Battery Level"
                    )
                    Text(text = "50 %")
                }
                LinearProgressIndicator(
                    progress = 0.5f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(bottom = 10.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Charge Current")
                    Text(text = "1.2 W / ${batteryChargingVM.currentVoltage.value} mA")
                }
                LinearProgressIndicator(
                    progress = 0.4f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(bottom = 10.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Voltage",
                    )
                    Text(text = "${batteryChargingVM.voltage.value} mV")
                }
                LinearProgressIndicator(
                    progress = 0.4f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(bottom = 10.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Temperature",
                    )
                    Text(text = "25.1 C")
                }
                LinearProgressIndicator(
                    progress = 0.4f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(bottom = 10.dp)
                )
            }

        }

    }


    if(batteryChargingVM.items.value.size >= 3){

        var startItem = batteryChargingVM.items.value[2];
        var endItem = batteryChargingVM.items.value[1];


        Text(
            text = "Last Charge Log",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 10.dp)
        )


        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Total Used")
                    Text("${(endItem.level!! - startItem.level!!)} %")
                }
            }
            Card(
                Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Charge State")
                    Text(if(endItem.isCharging!!) "Charging" else "Un Charge")
                }
            }

            Card(
                Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Column(
                    Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    Log.i("ChargingLog","Charging Since ${TimeUtility.betweenTwoDate(startItem.startTime,endItem.startTime)} ${TimeUtility.convertLongToOnlyTime(endItem.startTime)} - ${ TimeUtility.convertLongToOnlyTime(startItem.startTime)}")
                    Text(text = "Duration")

                    val time = TimeUtility.betweenTwoDate(startItem.startTime,endItem.startTime);
                    Text(time.ifEmpty { "Few Sec" })
                }

            }
        }

    }



}
