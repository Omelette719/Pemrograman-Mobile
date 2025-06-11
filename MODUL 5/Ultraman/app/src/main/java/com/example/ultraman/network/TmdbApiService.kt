package com.example.ultraman.network

import com.example.ultraman.model.SearchResponse
import com.example.ultraman.model.UltramanResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): UltramanResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): UltramanResponse

    @GET("search/multi")
    suspend fun searchUltraman(
        @Query("query") query: String = "Ultraman",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): SearchResponse

}

