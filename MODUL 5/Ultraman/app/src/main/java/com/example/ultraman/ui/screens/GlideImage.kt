package com.example.ultraman.ui.screens

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import androidx.compose.ui.layout.ContentScale

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
