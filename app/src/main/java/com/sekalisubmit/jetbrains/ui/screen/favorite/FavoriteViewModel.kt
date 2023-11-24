package com.sekalisubmit.jetbrains.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekalisubmit.jetbrains.data.IDERepository
import com.sekalisubmit.jetbrains.model.FavsIDE
import com.sekalisubmit.jetbrains.ui.common.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: IDERepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UIState<List<FavsIDE>>> = MutableStateFlow(UIState.Loading)
    val uiState: MutableStateFlow<UIState<List<FavsIDE>>>
        get() = _uiState

    fun getAllFavIDE() {
        viewModelScope.launch {
            repository.getFavIDE()
                .catch {
                    _uiState.value = UIState.Error(it.message.toString())
                }
                .collect { favsIDE ->
                    _uiState.value = UIState.Success(favsIDE)
                }

        }
    }
}