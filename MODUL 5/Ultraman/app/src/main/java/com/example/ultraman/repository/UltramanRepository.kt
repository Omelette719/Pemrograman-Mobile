package com.example.ultraman.repository

import android.util.Log
import com.example.ultraman.data.ApiResponse
import com.example.ultraman.data.UltramanDao
import com.example.ultraman.model.UltramanItem
import com.example.ultraman.model.toUltramanItem
import com.example.ultraman.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UltramanRepository(private val dao: UltramanDao) {

    val ultramanItems: Flow<List<UltramanItem>> = dao.getAll()
    val favoriteItems: Flow<List<UltramanItem>> = dao.getFavorites()

    fun refreshData(apiKey: String): Flow<ApiResponse<List<UltramanItem>>> = flow {
        emit(ApiResponse.Loading)

        try {
            val searchResponse = RetrofitInstance.api.searchUltraman(apiKey = apiKey)
            val newItems = searchResponse.results.mapNotNull { it.toUltramanItem() }

            val oldItems = dao.getAllOnce()
            val mergedItems = newItems.map { newItem ->
                val old = oldItems.find { it.id == newItem.id }
                newItem.copy(isFavorite = old?.isFavorite ?: false)
            }

            dao.insertAll(mergedItems.take(20))
            emit(ApiResponse.Success(mergedItems.take(20)))

        } catch (e: Exception) {
            Log.e("UltramanRepository", "Error: ${e.message}")
            emit(ApiResponse.Error(e.message ?: "Terjadi kesalahan"))
        }
    }

    suspend fun toggleFavorite(id: Int, currentValue: Boolean) {
        dao.setFavorite(id, !currentValue)
    }

    fun getFavorites(): Flow<List<UltramanItem>> {
        return dao.getFavorites()
    }
}
