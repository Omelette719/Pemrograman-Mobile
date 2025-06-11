package com.example.ultraman.ui.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ultraman.data.UltramanDatabase
import com.example.ultraman.repository.UltramanRepository

class UltramanViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = UltramanDatabase.getDatabase(context).ultramanDao()
        val repository = UltramanRepository(dao)
        @Suppress("UNCHECKED_CAST")
        return UltramanViewModel(repository) as T
    }
}
