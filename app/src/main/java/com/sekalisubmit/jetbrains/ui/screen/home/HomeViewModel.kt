package com.sekalisubmit.jetbrains.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekalisubmit.jetbrains.data.IDERepository
import com.sekalisubmit.jetbrains.model.FavsIDE
import com.sekalisubmit.jetbrains.ui.common.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: IDERepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState<List<FavsIDE>>> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<List<FavsIDE>>>
        get() = _uiState

    fun getAllIDE() {
        viewModelScope.launch {
            repository.getAllIDE()
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }
                .collect { orderItems ->
                    _uiState.value = UIState.Success(orderItems)
                }
        }
    }

    fun searchIDE(query: String) {
        viewModelScope.launch {
            repository.searchIDE(query)
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }
                .collect { ide ->
                    _uiState.value = UIState.Success(ide)
                }
        }
    }
}