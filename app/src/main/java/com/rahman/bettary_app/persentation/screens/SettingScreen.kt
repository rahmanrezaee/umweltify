package com.rahman.bettary_app.persentation.screens


//import androidx.compose.ui.platform.LocalContext
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.theme.Typography
import com.rahman.bettary_app.persentation.viewModel.SetupViewModel
import com.rahman.bettary_app.persentation.viewModel.ThemeState

//@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    nav: NavController = NavController(LocalContext.current),
    setupViewModel: SetupViewModel
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
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
        content = {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 65.dp),
            ) {
                item {
                    Text(
                        text = "General",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
                item {


                    var expandedTheme by remember { mutableStateOf(false) }

                    ListItem(
                        modifier = Modifier.clickable {
                            expandedTheme = !expandedTheme
                        },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.round_brightness),
                                contentDescription = null
                            )
                        },
                        overlineText = {
                            Text(
                                text = "Home",
                            )

                        },
                        headlineText = {
                            Text(
                                text = when (setupViewModel.themeState.value) {
                                    ThemeState.LIGHT_MODE -> {
                                        "Light Mode"
                                    }
                                    ThemeState.SYSTEM -> {
                                        "System Mode"
                                    }
                                    ThemeState.DARK_MODE -> {
                                        "Dark Mode"
                                    }
                                },
                            )
                        },
                        trailingContent = {
                            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                Icon(Icons.Rounded.KeyboardArrowRight, contentDescription = null)
                                DropdownMenu(
                                    expanded = expandedTheme,
                                    onDismissRequest = { expandedTheme = false },
                                    offset = DpOffset(0.dp, -20.dp),
                                    modifier = Modifier
                                        .padding(0.dp)
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            Text("System")
                                        },
                                        onClick = {
                                            setupViewModel.changeTheme(ThemeState.SYSTEM)
                                            expandedTheme = !expandedTheme
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text("Dark")
                                        },
                                        onClick = {

                                            setupViewModel.changeTheme(ThemeState.DARK_MODE)
                                            expandedTheme = !expandedTheme
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text("Light")
                                        },
                                        onClick = {
                                            setupViewModel.changeTheme(ThemeState.LIGHT_MODE)
                                            expandedTheme = !expandedTheme
                                        }
                                    )
                                }
                            }


                        }
                    )


                }

            }

        }

    )
}

