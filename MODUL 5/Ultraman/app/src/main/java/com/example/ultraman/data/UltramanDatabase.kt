package com.example.ultraman.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ultraman.model.UltramanItem

@Database(entities = [UltramanItem::class], version = 3, exportSchema = false)
abstract class UltramanDatabase : RoomDatabase() {
    abstract fun ultramanDao(): UltramanDao

    companion object {
        @Volatile
        private var INSTANCE: UltramanDatabase? = null

        fun getDatabase(context: Context): UltramanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UltramanDatabase::class.java,
                    "ultraman_db"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
