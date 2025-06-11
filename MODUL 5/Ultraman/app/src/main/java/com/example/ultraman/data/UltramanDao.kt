package com.example.ultraman.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ultraman.model.UltramanItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UltramanDao {
    @Query("SELECT * FROM ultraman")
    fun getAll(): Flow<List<UltramanItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<UltramanItem>)

    @Query("UPDATE ultraman SET isFavorite = :isFav WHERE id = :id")
    suspend fun setFavorite(id: Int, isFav: Boolean)

    @Query("SELECT * FROM ultraman WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<UltramanItem>>

    @Query("SELECT * FROM ultraman")
    suspend fun getAllOnce(): List<UltramanItem>
}
