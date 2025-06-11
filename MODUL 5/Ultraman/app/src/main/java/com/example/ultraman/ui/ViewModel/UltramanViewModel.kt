package com.example.ultraman.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultraman.data.ApiResponse
import com.example.ultraman.model.UltramanItem
import com.example.ultraman.repository.UltramanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UltramanViewModel(private val repository: UltramanRepository) : ViewModel() {

    private val _ultramanList = MutableStateFlow<List<UltramanItem>>(emptyList())
    val ultramanList: StateFlow<List<UltramanItem>> = _ultramanList

    private val _favoriteList = MutableStateFlow<List<UltramanItem>>(emptyList())
    val favoriteList: StateFlow<List<UltramanItem>> = _favoriteList

    private val _uiState = MutableStateFlow<ApiResponse<List<UltramanItem>>>(ApiResponse.Loading)
    val uiState: StateFlow<ApiResponse<List<UltramanItem>>> = _uiState

    private val _favoriteState = MutableStateFlow<ApiResponse<List<UltramanItem>>>(ApiResponse.Loading)
    val favoriteState: StateFlow<ApiResponse<List<UltramanItem>>> = _favoriteState

    init {
        refreshData()
        observeLocalData()
    }

    private fun observeLocalData() {
        viewModelScope.launch {
            repository.ultramanItems.collectLatest {
                _ultramanList.value = it
                _uiState.value = ApiResponse.Success(it)
            }
        }

        viewModelScope.launch {
            try {
                repository.favoriteItems.collectLatest {
                    _favoriteList.value = it
                    _favoriteState.value = ApiResponse.Success(it)
                }
            } catch (e: Exception) {
                _favoriteState.value = ApiResponse.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun refreshData(apiKey: String = "5747e2247c220f5aac1c7654bfa77d4b") {
        viewModelScope.launch {
            repository.refreshData(apiKey).collectLatest {
                _uiState.value = it
            }
        }
    }

    fun getItemById(id: Int): UltramanItem? {
        return _ultramanList.value.find { it.id == id }
    }

    fun toggleFavorite(item: UltramanItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item.id, item.isFavorite)
        }
    }
}
