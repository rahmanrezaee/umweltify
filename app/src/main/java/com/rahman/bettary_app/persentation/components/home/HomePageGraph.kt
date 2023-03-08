package com.rahman.bettary_app.persentation.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import com.rahman.bettary_app.network.responses.DashboardResponse
import com.rahman.bettary_app.persentation.theme.Teal200
import com.rahman.bettary_app.persentation.util.RequestState
import com.rahman.bettary_app.persentation.util.TimeUtility
import com.rahman.bettary_app.persentation.viewModel.BatteryChargingViewModel


@Composable
fun HomeGraph(batteryCharging: BatteryChargingViewModel) {


    val style2 = LineGraphStyle(
        visibility = LinearGraphVisibility(
            isHeaderVisible = true,
            isYAxisLabelVisible = false,
            isGridVisible = true,
            isXAxisLabelVisible = true,
            isCrossHairVisible = true,
        ),

        colors = LinearGraphColors(
            lineColor = Teal200,
            pointColor = Teal200,
            clickHighlightColor = Teal200,
        )
    )

    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
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


            val allItems by batteryCharging.dashboardItems.collectAsState()

            if (allItems is RequestState.Success ) {

                val response: DashboardResponse =
                    (allItems as RequestState.Success<DashboardResponse>).data

                val xData: List<String> = response.data.data.map {
                    it.date
                }.toList()
                val yData: List<Double> = response.data.data.map {
                    it.value * 100
                }.toList()

                Text(text = response.data.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),)

                LineGraph(
                    style = style2,
                    xAxisData = xData.map {
                        GraphData.String(TimeUtility.getMonth(it))
                    }, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
                    yAxisData = yData,
                )

            } else if (allItems is RequestState.Loading || allItems is RequestState.LoadingRefresh) {

                Box(
                    Modifier
                        .height((LocalConfiguration.current.screenWidthDp/2).dp)
                        .fillMaxWidth(),
                    Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }
            else  {

                Box(
                    Modifier
                        .height((LocalConfiguration.current.screenWidthDp/2).dp)
                        .fillMaxWidth(),
                    Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Server Error")
                        TextButton(onClick = {
                            batteryCharging.getDashboardData()
                        }) {
                            Text(text = "Retry")
                        }
                    }
                }

            }
        }

    }
}
