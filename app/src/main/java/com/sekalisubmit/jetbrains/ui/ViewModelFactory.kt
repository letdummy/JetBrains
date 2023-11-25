package com.sekalisubmit.jetbrains.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sekalisubmit.jetbrains.data.IDERepository
import com.sekalisubmit.jetbrains.ui.screen.detail.DetailViewModel
import com.sekalisubmit.jetbrains.ui.screen.favorite.FavoriteViewModel
import com.sekalisubmit.jetbrains.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: IDERepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}