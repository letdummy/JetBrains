package com.sekalisubmit.jetbrains.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekalisubmit.jetbrains.data.IDERepository
import com.sekalisubmit.jetbrains.model.FavsIDE
import com.sekalisubmit.jetbrains.ui.common.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: IDERepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState<FavsIDE>> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<FavsIDE>>
        get() = _uiState

    fun getIDEbyId(ideId: Long) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            _uiState.value = UIState.Success(repository.getIDEById(ideId))
        }
    }

    fun addFavIDE(ideId: Long) {
        viewModelScope.launch {
            repository.updateFavIDE(ideId, true)
        }
    }
}