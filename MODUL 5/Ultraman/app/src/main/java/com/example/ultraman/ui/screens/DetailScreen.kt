package com.example.ultraman.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ultraman.ui.ViewModel.UltramanViewModel

@Composable
fun DetailScreen(itemId: Int?, viewModel: UltramanViewModel) {
    val item = itemId?.let { viewModel.getItemById(it) }
    Log.d("DetailScreen", "Navigasi ke DetailScreen untuk ID: $itemId - ${item?.title}")

    item?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(WindowInsets.systemBars.asPaddingValues())
                .verticalScroll(rememberScrollState())
        ) {
            it.getPosterUrl()?.let { posterUrl ->
                GlideImage(
                    imageUrl = posterUrl,
                    contentDescription = it.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = it.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text( text = "Tahun: ",
                  fontWeight = FontWeight.Bold
            )
            Text(it.year)
            Text(
                text = "Sinopsis:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = it.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify
            )
        }
    } ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Data tidak ditemukan.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
