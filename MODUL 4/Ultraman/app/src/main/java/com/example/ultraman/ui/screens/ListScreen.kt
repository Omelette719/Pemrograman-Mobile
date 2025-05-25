package com.example.ultraman.ui.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.core.net.toUri
import com.example.ultraman.ui.ViewModel.UltramanViewModel

@Composable
fun ListScreen(navController: NavHostController, viewModel: UltramanViewModel) {
    val ultramanList = viewModel.ultramanList.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        items(ultramanList) { item ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    GlideImage(
                        imageUrl = item.imageUrl,
                        contentDescription = item.Name,
                        modifier = Modifier
                            .height(150.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.Name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = item.HumanHost,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Info: ",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.Detail,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            Button(
                                onClick = {
                                    Log.d("ListScreen", "Tombol Wiki ditekan untuk ${item.Name}")
                                    val intent = Intent(Intent.ACTION_VIEW, item.link.toUri())
                                    navController.context.startActivity(intent)
                                }
                            ) {
                                Text("Wiki")
                            }

                            Button(
                                onClick = {
                                    Log.d("ListScreen", "Tombol Detail ditekan untuk ${item.Name}")
                                    navController.navigate("detail/${item.id}")
                                }
                            ) {
                                Text("Detail")
                            }
                        }
                    }
                }
            }
        }
    }
}


