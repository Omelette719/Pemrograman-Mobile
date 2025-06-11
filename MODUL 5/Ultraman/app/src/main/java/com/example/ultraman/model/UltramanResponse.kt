package com.example.ultraman.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UltramanResponse(
    val id: Int,
    @SerialName("name") val name: String? = null,
    @SerialName("title") val title: String? = null,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,

    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null
)

