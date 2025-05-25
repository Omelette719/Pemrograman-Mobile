package com.example.ultraman.ui.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ultraman.model.ListUltraman
import com.example.ultraman.ultramans
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UltramanViewModel : ViewModel() {

    private val _ultramanList = MutableStateFlow<List<ListUltraman>>(emptyList())
    val ultramanList: StateFlow<List<ListUltraman>> = _ultramanList

    init {
        _ultramanList.value = ultramans
        Log.d("UltramanViewModel", "Data list dimasukkan ke ViewModel (${ultramans.size} item)")
    }

    fun getItemById(id: Int): ListUltraman? {
        return _ultramanList.value.find { it.id == id }
    }
}

class UltramanViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UltramanViewModel() as T
    }
}
