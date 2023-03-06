package com.rahman.bettary_app.persentation.screens

//import androidx.compose.ui.platform.LocalContext
import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.rahman.bettary_app.persentation.components.AddressFormBottomSheet
import com.rahman.bettary_app.persentation.components.general.RequestPermissionComponent
import com.rahman.bettary_app.persentation.components.general.customRememberPermissionState
import com.rahman.bettary_app.persentation.components.general.showAlertSetting
import kotlin.random.Random

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddressScreen(nav: NavController = NavController(LocalContext.current)) {
//    var context = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Address Page")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog = !openDialog
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))

            ) {
                Icon(Icons.Filled.Add, null)
            }
        },

        content = {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 65.dp),
            ) {
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
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .fillMaxWidth()

                    ) {
                        Box(
                            Modifier
                                .randomBackground()
                                .padding(start = 5.dp)
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "Home", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

                            }
                        }

                    }


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
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .fillMaxWidth()

                    ) {
                        Box(
                            Modifier
                                .randomBackground()
                                .padding(start = 5.dp)
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "Office", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

                            }
                        }

                    }


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
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .fillMaxWidth()

                    ) {
                        Box(
                            Modifier
                                .randomBackground()
                                .padding(start = 5.dp)
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "Other", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

                            }
                        }

                    }


                }
            }

        }

    )



    if (openDialog) {

        Dialog(
            onDismissRequest = {
                openDialog = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // experimental
            )
        ) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Address Form")
                        },
                        actions = {
                            IconButton(onClick = {
                                openDialog = false
                            }) {
                                Icon(Icons.Rounded.Close, contentDescription = null )
                            }
                        }
                    )
                },
                content = {
                    Column(modifier = Modifier
                        .padding(top = 75.dp, start = 15.dp, end = 15.dp, bottom = 16.dp)
                        .fillMaxSize(),
                    ) {

                        val pushPermissionState = customRememberPermissionState(
                            permission = Manifest.permission.ACCESS_FINE_LOCATION,
                            onCannotRequestPermission = {
                                context.showAlertSetting(
                                    "Location Permission",
                                    "Location",
                                    context.packageName
                                )
                            }
                        )
                        if (pushPermissionState.status.isGranted) {
                            Column() {
                                AddressFormBottomSheet {
                                    openDialog = false
                                }
                            }

                        } else {
                            RequestPermissionComponent(title = "You haven't Location Permission") {
                                pushPermissionState.launchPermissionRequest()
                            }
                        }

                    }
                }
            )
        }
    }
}


val RandomColor
    get() = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

fun Modifier.randomBackground() = this.then(
    background(RandomColor)
)


