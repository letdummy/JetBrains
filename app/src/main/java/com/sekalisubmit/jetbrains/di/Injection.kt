package com.sekalisubmit.jetbrains.di

import com.sekalisubmit.jetbrains.data.IDERepository

object Injection {
    fun provideRepository(): IDERepository {
        return IDERepository.getInstance()
    }
}