package com.rahman.bettary_app.persentation.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import com.rahman.bettary_app.persentation.theme.Teal200


@Composable
fun HomeGraph() {
    val style2 = LineGraphStyle(
        visibility = LinearGraphVisibility(
            isHeaderVisible = true,
            isYAxisLabelVisible = true,
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
            LineGraph(
                style = style2,
                xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
                    GraphData.String(it)
                }, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
                yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
            )

        }

    }
}
