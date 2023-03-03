

package com.rahman.bettary_app.persentation.screens.dashboard.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.orbitalsonic.waterwave.WaterWaveView
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.components.HomeGraph
import com.rahman.bettary_app.persentation.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CheckResult", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage() {
//    val batteryViewModel: BatteryViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Dashboard",
                        maxLines = 1,
                        style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)

        ) {
            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                )
            }
            item{

                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White,

                        ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 20.dp
                    ),
                    shape = RoundedCornerShape(5),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()

                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        HomeGraph()
                    }
                }

            }

            item {

                Row(
                    Modifier.height(250.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)

                    ) {

                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,

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

                                Text(text = "Status",style = MaterialTheme.typography.labelSmall)
                                Text(text = "Charging", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painterResource(
                                        id = R.drawable.charging),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.rotate(90F)
                                )
                            }
                        }

                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,
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

                                Text(text = "Status",style = MaterialTheme.typography.labelSmall)
                                Text(text = "Charging", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary )
                            }
                        }
                    }
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                        ), elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 20.dp
                        ),

                        shape = RoundedCornerShape(10),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Box {
                            AndroidView({ context->
                                WaterWaveView(context).also { waterWaveView ->
                                    waterWaveView.setShape(WaterWaveView.Shape.SQUARE)
                                    waterWaveView.setFrontWaveColor(android.graphics.Color.parseColor("#03A1A0"))
                                    waterWaveView.setBehindWaveColor(android.graphics.Color.parseColor("#FF018786"))
                                    waterWaveView.setBorderColor(R.color.transparent)
                                    waterWaveView.setBorderWidth(0f)
                                    waterWaveView.setAnimationSpeed(50)
                                    waterWaveView.offsetTopAndBottom(200)
                                    waterWaveView.setWaveStrong(100)
                                    waterWaveView.setHideText(true)
                                    waterWaveView.scaleY = 1.5f
                                    waterWaveView.startAnimation()
                                }

                            })
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                Arrangement.Center,
                                Alignment.CenterHorizontally
                            ) {

                                Text(text = "Battery Level",style = MaterialTheme.typography.labelSmall)
                                Text(text = "89%", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "3900mAh",style = MaterialTheme.typography.labelSmall)

                            }


                        }
                    }
                }


            }
            item {

                Row(
                    Modifier.height(250.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)

                    ) {

                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,

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

                                Text(text = "Status",style = MaterialTheme.typography.labelSmall)
                                Text(text = "Charging", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painterResource(
                                        id = R.drawable.charging),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.rotate(90F)
                                )
                            }
                        }

                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,
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
                                Text(text = "Status",style = MaterialTheme.typography.labelSmall)
                                Text(text = "Charging", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f)

                    ) {

                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,

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

                                Text(text = "Status",style = MaterialTheme.typography.labelSmall)
                                Text(text = "Charging", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    painterResource(
                                        id = R.drawable.charging),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.rotate(90F)
                                )
                            }
                        }

                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,
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

                                Text(text = "Status",style = MaterialTheme.typography.labelSmall)
                                Text(text = "Charging", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary )
                            }
                        }
                    }
                }


//            HomeGraph()
            }
            item {

//            HomeChargingInfo()
            }

            item {

//            HomeDeviceInfo()
            }
            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                )
            }
        }

    }

}

