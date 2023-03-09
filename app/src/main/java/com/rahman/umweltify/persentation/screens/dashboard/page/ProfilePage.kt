package com.rahman.umweltify.persentation.screens.dashboard.page

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.routes.Routes
import com.rahman.umweltify.persentation.theme.Typography
import com.rahman.umweltify.persentation.viewModel.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(mainNav: NavController,authViewModel: AuthViewModel) {

    val context = LocalContext.current;
    val localConfig = LocalConfiguration.current


    Scaffold(
        topBar = {
           ProfileTopBar(){
               authViewModel.logout(mainNav);
           }
        },
    ) {


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)

        ) {
            Spacer(modifier = Modifier.height(65.dp))
            Column(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.Center,
                    Alignment.CenterVertically
                ) {

                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(10.dp),
                            shape = CircleShape,
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,
                            ),

                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 20.dp
                            ),
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data("https://img.freepik.com/free-photo/portrait-happy-young-woman-looking-camera_23-2147892777.jpg?w=2000")
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.placehoder_user),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        SmallFloatingActionButton(
                            modifier = Modifier.padding(0.dp),
                            onClick = {
                            }) {
                            Icon(
                                Icons.Rounded.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                }

                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Thomas Newman",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )

            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 20.dp
                ),
            ) {

                LazyColumn(modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp)) {
                    item {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    mainNav.navigate(Routes.AddressScreen.name)
                                }
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Address")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(id = R.drawable.feedback),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Feedbacks")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(id = R.drawable.round_star),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Rate Us")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                        }
                    }
                    item {
                        Divider(
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.Info, contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "About Us")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.Call,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Contact us")
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                        }
                    }


                }

            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(onLogout:()->Unit = {}) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                "Profile",
                maxLines = 1,
                style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = {
                expanded = !expanded
            }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Localized description"
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(0.dp, -10.dp),
                    modifier = Modifier.padding(0.dp)
                ) {
                    DropdownMenuItem(
                        text ={
                            Text("Logout")
                        },
                        onClick = {
                            onLogout.invoke()
                        }
                    )
                }
            }
        }
    )
}
