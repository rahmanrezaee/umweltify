package com.rahman.umweltify.persentation.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.rahman.umweltify.R
import com.rahman.umweltify.network.responses.DashboardResponse
import com.rahman.umweltify.persentation.util.RequestState
import com.rahman.umweltify.persentation.util.TimeUtility
import com.rahman.umweltify.persentation.viewModel.BatteryChargingViewModel


@Composable
fun HomeGraph(batteryCharging: BatteryChargingViewModel) {


    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 20.dp
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {


            val allItems by batteryCharging.dashboardItems.collectAsState()

            val deviceItems by batteryCharging.dashboardItemsDevice.collectAsState()


            val locationItems by batteryCharging.dashboardItemsLocation.collectAsState()
            val defaultLines = currentChartStyle.lineChart.lines

            var startAxisStyle = startAxis(
                label = axisLabelComponent(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                guideline = LineComponent(
                    color = R.color.transparent,
                    strokeColor = R.color.transparent,
                    strokeWidthDp = 0f,
                    thicknessDp = 0f
                ),
            )
            var charType = lineChart(
                remember(defaultLines) {
                    defaultLines.map { defaultLine -> defaultLine.copy(lineBackgroundShader = null) }
                },
            )
            var bottomAxisStyle = bottomAxis(
                tickPosition = HorizontalAxis.TickPosition.Edge,
                valueFormatter = axisValueFormatter,
                label = axisLabelComponent(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                guideline = LineComponent(
                    color = R.color.transparent,
                    strokeColor = R.color.transparent,
                    strokeWidthDp = 0f,
                    thicknessDp = 0f
                ),
            )


            if (allItems is RequestState.Success) {

                val response: DashboardResponse =
                    (allItems as RequestState.Success<DashboardResponse>).data

                val chartEntryModel: List<FloatEntry> = response.data.data.map {
                    FloatEntry(
                        TimeUtility.getMonthNumber(it.date),
                        String.format("%.1f",it.value).toFloat()
                    )
                }.toList()


                Text(
                    text = response.data.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )


                val dashboardData = ChartEntryModelProducer(chartEntryModel)

                Chart(
                    chartModelProducer = dashboardData,
                    modifier = Modifier.height(120.dp),
                    chart = charType,
                    startAxis = startAxisStyle,
                    bottomAxis = bottomAxisStyle,
                )


            } else if (allItems is RequestState.Loading || allItems is RequestState.LoadingRefresh) {

                Box(
                    Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {

                Box(
                    Modifier
                        .height(120.dp)
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

            if (deviceItems is RequestState.Success) {

                val response: DashboardResponse =
                    (deviceItems as RequestState.Success<DashboardResponse>).data

                val chartEntryModel: List<FloatEntry> = response.data.data.map {
                    FloatEntry(
                        TimeUtility.getMonthNumber(it.date),
                        String.format("%.1f",it.value).toFloat()
                    )
                }.toList()


                Text(
                    text = response.data.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )
                val dashboardData = ChartEntryModelProducer(chartEntryModel)

                Chart(

                    chartModelProducer = dashboardData,
                    modifier = Modifier.height(120.dp),
                    chart = charType,
                    startAxis = startAxisStyle,
                    bottomAxis = bottomAxisStyle,
                )


            } else if (deviceItems is RequestState.Loading || deviceItems is RequestState.LoadingRefresh) {

                Box(
                    Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {

                Box(
                    Modifier
                        .height(120.dp)
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


            if (locationItems is RequestState.Success) {

                val response: DashboardResponse =
                    (locationItems as RequestState.Success<DashboardResponse>).data

                val chartEntryModel: List<FloatEntry> = response.data.data.map {
                    FloatEntry(
                        TimeUtility.getMonthNumber(it.date),
                        String.format("%.1f",it.value).toFloat()
                    )
                }.toList()


                Text(
                    text = response.data.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                )

                val dashboardLocationData = ChartEntryModelProducer(chartEntryModel)

                Chart(

                    chartModelProducer = dashboardLocationData,
                    modifier = Modifier.height(120.dp),
                    chart = charType,
                    startAxis = startAxisStyle,
                    bottomAxis = bottomAxisStyle,
                )
            } else if (locationItems is RequestState.Loading || locationItems is RequestState.LoadingRefresh) {

                Box(
                    Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {

                Box(
                    Modifier
                        .height(120.dp)
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


private val axisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { i, _ ->

    when (i) {
        1f -> {
            "Jen"
        }
        2f -> {
            "Feb"
        }
        3f -> {
            "Mar"
        }
        4f -> {
            "Apr"
        }
        5f -> {
            "May"
        }
        6f -> {
            "Jon"
        }
        7f -> {
            "Jul"
        }
        8f -> {
            "Agu"
        }
        9f -> {
            "Sep"
        }
        10f -> {
            "Oct"
        }
        11f -> {
            "Nav"
        }
        12f -> {
            "Dec"
        }
        else -> {
            ""
        }
    }
}
