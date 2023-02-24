package com.rahman.bettary_app.persentation.components

import androidx.compose.runtime.Composable
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility


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
//            colors = LinearGraphColors(
//                lineColor = Color.Green,
////                pointColor = GraphAccent2,
////                clickHighlightColor = PointHighlight2,
////                fillGradient = Brush.verticalGradient(
////                    listOf(Gradient3, Gradient2)
////                )
//            )
    )

    LineGraph(
        style = style2,
        xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
            GraphData.String(it)
        }, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
        yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
    )
}