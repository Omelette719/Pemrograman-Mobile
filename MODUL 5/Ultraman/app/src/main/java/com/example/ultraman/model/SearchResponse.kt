package com.example.ultraman.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val results: List<UltramanResponse>
)
