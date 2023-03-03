package com.rahman.bettary_app.persentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility
import com.rahman.bettary_app.persentation.theme.Teal200


@Composable
fun HomeGraph() {

//        // LineGraph
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

    LineGraph(
        style = style2,
        xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
            GraphData.String(it)
        }, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
        yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
    )
}