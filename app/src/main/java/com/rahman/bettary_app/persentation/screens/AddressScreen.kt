package com.rahman.bettary_app.persentation.screens

//import androidx.compose.ui.platform.LocalContext
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.rahman.bettary_app.persentation.components.AddressFormBottomSheet
import com.rahman.bettary_app.persentation.components.LocationPermissionHandler
import kotlinx.coroutines.launch
import kotlin.random.Random

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddressScreen(nav: NavController = NavController(LocalContext.current)) {
//    var context = LocalContext.current


    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberSheetState()

    LocationPermissionHandler {
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
//                    openBottomSheet = !openBottomSheet
                },
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
                items(5) {

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
                            Modifier.randomBackground().padding(start = 5.dp).background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(text = "Home", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp))

                            }
                        }

                    }


                }
            }

        }
    )

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = bottomSheetState,
            ) {
                Column() {
                    AddressFormBottomSheet {
                        scope.launch {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    openBottomSheet = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}


val RandomColor
    get() = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

fun Modifier.randomBackground() = this.then(
    background(RandomColor)
)


