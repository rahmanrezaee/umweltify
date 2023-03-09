package com.rahman.umweltify.persentation.screens.dashboard.page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionPage () {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Action",
                        maxLines = 1,
                        style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        overflow = TextOverflow.Ellipsis
                    )
                },

            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally

        ){
            Text(text = "This Feature is under Development",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
                )
            Spacer(modifier = Modifier.height(10.dp))
            Image(painter = painterResource(id = R.drawable.coding),
                modifier = Modifier.padding(30.dp),
                contentDescription = null)
        }
    }
}