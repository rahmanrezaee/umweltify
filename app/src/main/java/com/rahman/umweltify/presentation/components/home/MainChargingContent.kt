package com.rahman.umweltify.presentation.components.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.orbitalsonic.waterwave.WaterWaveView
import com.rahman.umweltify.R
import com.rahman.umweltify.presentation.viewModel.BatteryChargingViewModel
import com.rahman.umweltify.presentation.viewModel.SetupViewModel
import kotlin.math.roundToInt

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainChargingContent(batteryCharging: BatteryChargingViewModel, setupViewModel: SetupViewModel) {
    Row(
        Modifier.height(250.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)

        ) {

//            ElevatedCard(
//                colors = CardDefaults.elevatedCardColors(
//                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
//
//                    ),
//                elevation = CardDefaults.elevatedCardElevation(
//                    defaultElevation = 20.dp
//                ),
//                shape = RoundedCornerShape(10),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .fillMaxWidth()
//                    .weight(1f)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(10.dp)
//                ) {
//
//                    Text(text = "Temperature", style = MaterialTheme.typography.labelSmall)
//
//                    var temperature: Double? =
//                        batteryCharging.chargeState.value?.temperature?.div(10.0)
//                    Text(
//                        text = setupViewModel.convertTemp(temperature ?: 0.0),
//                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Icon(
//                        painterResource(
//                            id = R.drawable.round_air
//                        ),
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.size(25.dp)
//                    )
//                }
//            }

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

                    Text(text = "Status", style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = "${batteryCharging.chargeState.value?.status()}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painterResource(
                            id = R.drawable.charging
                        ),
                        contentDescription = null,
                        modifier =
                        Modifier
                            .size(25.dp)
                            .rotate(90F),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
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
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Box {
                AndroidView({ context ->
                    WaterWaveView(context).also { waterWaveView ->
                        waterWaveView.setShape(WaterWaveView.Shape.SQUARE)
                        waterWaveView.setFrontWaveColor(
                            android.graphics.Color.parseColor(
                                "#03A1A0"
                            )
                        )
                        waterWaveView.setBehindWaveColor(
                            android.graphics.Color.parseColor(
                                "#FF018786"
                            )
                        )
                        waterWaveView.progress = 0
                        waterWaveView.max = 120
                        waterWaveView.setBorderColor(R.color.transparent)
                        waterWaveView.setBorderWidth(0f)
                        waterWaveView.setAnimationSpeed(50)
                        waterWaveView.offsetTopAndBottom(200)
                        waterWaveView.setWaveStrong(70)
                        waterWaveView.setHideText(true)
                        waterWaveView.scaleY = 1.35f
                        waterWaveView.startAnimation()
                    }
                },
                    update = {
                        it.progress = (batteryCharging.chargeState.value?.level ?: 0) + 20
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    Arrangement.Center,
                    Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Battery Level",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = "${batteryCharging.chargeState.value?.level ?: 0}%",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    var cap = Math.round(
                        (batteryCharging.batteryInfo.value?.batteryCapacity?.div(100000)?.roundToInt()?.toDouble()?.times(100) ?: 0.0)
                    )
                    Text(text = "${cap} mAh", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }


}