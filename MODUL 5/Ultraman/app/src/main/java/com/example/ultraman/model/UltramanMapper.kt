package com.example.ultraman.model

fun UltramanResponse.toUltramanItem(): UltramanItem {
    return UltramanItem(
        id = id,
        title = title ?: name ?: "Unknown Title",
        description = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        firstAirDate = firstAirDate
    )
}
