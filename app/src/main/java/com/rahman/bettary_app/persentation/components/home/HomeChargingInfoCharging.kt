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
fun HomeChargingInfoCharging() {


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