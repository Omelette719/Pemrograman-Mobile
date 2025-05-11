package com.example.ultraman.ui.screens

import android.widget.ImageView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.ultraman.ultramans

@Composable
fun DetailScreen(itemId: Int?) {
    val item = ultramans.find { it.id == itemId }
    item?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(WindowInsets.systemBars.asPaddingValues())
                .verticalScroll(rememberScrollState())
        ) {
            GlideImage(
                imageUrl = it.imageUrl,
                contentDescription = it.Name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(it.Name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text( text = "Human Host: ", fontWeight = FontWeight.Bold)
            Text(it.HumanHost)
            Text( text = "Height: ", fontWeight = FontWeight.Bold)
            Text(it.Height)
            Text( text = "Weight: ", fontWeight = FontWeight.Bold)
            Text(it.Weight)
            Text( text = "\nUltraman Info: ", fontWeight = FontWeight.Bold)
            Text( text = it.Detail, textAlign = TextAlign.Justify)
        }
    }
}

@Composable
fun GlideImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            ImageView(context).apply {
                scaleType = when (contentScale) {
                    ContentScale.Crop -> ImageView.ScaleType.CENTER_CROP
                    ContentScale.Fit -> ImageView.ScaleType.FIT_CENTER
                    else -> ImageView.ScaleType.CENTER_CROP
                }
                contentDescription?.let { this.contentDescription = it }
            }
        },
        update = {
            Glide.with(context)
                .load(imageUrl)
                .into(it)
        },
        modifier = modifier
    )
}

