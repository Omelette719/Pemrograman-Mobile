package com.example.ultraman.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.ultraman.ui.ViewModel.UltramanViewModel
import com.example.ultraman.data.ApiResponse
import com.example.ultraman.ui.screens.GlideImage

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: UltramanViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.favoriteState.collectAsState().value

    when (uiState) {
        is ApiResponse.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ApiResponse.Error -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Terjadi kesalahan: ${uiState.message}", color = Color.Red)
            }
        }

        is ApiResponse.Success -> {
            val favoriteList = uiState.data

            if (favoriteList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada yang difavoritkan", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(favoriteList) { item ->
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
                                Box(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .width(100.dp)
                                ) {
                                    GlideImage(
                                        imageUrl = item.getPosterUrl().toString(),
                                        contentDescription = item.title,
                                        modifier = Modifier
                                            .matchParentSize()
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    IconButton(
                                        onClick = { viewModel.toggleFavorite(item) },
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(4.dp)
                                            .background(Color.White.copy(alpha = 0.5f), shape = CircleShape)
                                    ) {
                                        Icon(
                                            imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = "Favorite",
                                            tint = Color.Red
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = item.title.take(20) + if (item.title.length > 20) "…" else "",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = item.year,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Info: ",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = item.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 4,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_VIEW, item.getTmdbLink().toUri())
                                                navController.context.startActivity(intent)
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("More info")
                                        }

                                        Button(
                                            onClick = {
                                                navController.navigate("detail/${item.id}")
                                            },
                                            modifier = Modifier.weight(1f)
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
        }
    }
}
