package com.example.ultraman.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "ultraman")
data class UltramanItem(
    @PrimaryKey val id: Int,
    val title: String,

    @SerialName("overview")
    val description: String,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    val isFavorite: Boolean = false
) {
    val year: String
        get() = releaseDate?.take(4) ?: firstAirDate?.take(4) ?: "????"

    fun getPosterUrl(): String? = posterPath?.let {
        "https://image.tmdb.org/t/p/w500$it"
    }

    fun getTmdbLink(): String {
        return if (!releaseDate.isNullOrBlank()) {
            "https://www.themoviedb.org/movie/$id"
        } else {
            "https://www.themoviedb.org/tv/$id"
        }
    }
}
