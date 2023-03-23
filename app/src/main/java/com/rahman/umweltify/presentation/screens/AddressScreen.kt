package com.rahman.umweltify.presentation.screens

//import androidx.compose.ui.platform.LocalContext
import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.rahman.umweltify.R
import com.rahman.umweltify.db.entity.AddressED
import com.rahman.umweltify.presentation.components.AddressFormBottomSheet
import com.rahman.umweltify.presentation.components.general.RequestPermissionComponent
import com.rahman.umweltify.presentation.components.general.customRememberPermissionState
import com.rahman.umweltify.presentation.components.general.showAlertSetting
import com.rahman.umweltify.presentation.theme.Typography
import com.rahman.umweltify.presentation.util.RequestState
import com.rahman.umweltify.presentation.viewModel.AddressViewModel
import kotlinx.coroutines.*
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import kotlin.random.Random

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddressScreen(nav: NavController = NavController(LocalContext.current)) {
//    var context = LocalContext.current
    val addressVM: AddressViewModel = hiltViewModel()

    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Address Page",
                        maxLines = 1,
                        style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        nav.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
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


            val allItems by addressVM.items.collectAsState()

            if (allItems is RequestState.Success) {

                var innerItem: List<AddressED> =
                    (allItems as RequestState.Success<List<AddressED>>).data

                if (innerItem.isEmpty()) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No Address Found.",
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(70.dp))
                        }
                        items(innerItem.size) {
                            val delete = SwipeAction(
                                icon = {
                                    Icon(
                                        Icons.TwoTone.Delete,
                                        tint = Color.White,
                                        modifier = Modifier.padding(10.dp),
                                        contentDescription = null
                                    )
                                },
                                background = MaterialTheme.colorScheme.primary,
                                onSwipe = {
                                    addressVM.deleteAddress(innerItem[it])
                                }
                            )
                            SwipeableActionsBox(
//                                startActions = listOf(delete),
                                swipeThreshold = 100.dp,
                                endActions = listOf(delete),
                            ) {

                                ListItem(
                                    modifier = Modifier.clickable {
                                        addressVM.selectAddress(innerItem[it])
                                    },
                                    leadingContent = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_my_location),
                                            contentDescription = null
                                        )
                                    },
                                    supportingText = {
                                        Text(
                                            text = "(${innerItem[it].latitude},${innerItem[it].longitude})",
                                        )
                                    },
                                    headlineText = {
                                        Text(
                                            text = innerItem[it].placeName,
                                        )
                                    },
                                    trailingContent = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_check_circle),
                                            tint = if (addressVM.selectedAddress.value == innerItem[it].placeName) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim,
                                            contentDescription = null
                                        )

                                    }
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
                        navigationIcon = {
                            IconButton(onClick = {
                                openDialog = false
                            }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
                            }
                        },

                        )
                },
                content = {
                    Column(
                        modifier = Modifier
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
                                AddressFormBottomSheet(
                                    addressVM = addressVM,
                                ) {
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


