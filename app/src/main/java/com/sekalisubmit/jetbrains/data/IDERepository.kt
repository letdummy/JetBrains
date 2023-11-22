package com.sekalisubmit.jetbrains.data

import com.sekalisubmit.jetbrains.model.FakeIDEData
import com.sekalisubmit.jetbrains.model.FavsIDE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class IDERepository {

    private val ides = mutableListOf<FavsIDE>()

    init {
        if (ides.isEmpty()){
            FakeIDEData.dummyIDE.forEach {
                ides.add(FavsIDE(it, false))
            }
        }
    }

    fun getAllIDE(): Flow<List<FavsIDE>> {
        return flowOf(ides)
    }

    fun getIDEById(ideId: Long): FavsIDE {
        return ides.first {
            it.ide.id == ideId
        }
    }

    fun updateFavIDE(ideId: Long, newFavValue: Boolean): Flow<Boolean> {
        val index = ides.indexOfFirst { it.ide.id == ideId }
        val result = if (index >= 0) {
            val favIDE = ides[index]
            ides[index] =
                favIDE.copy(ide = favIDE.ide, isFav = newFavValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavIDE(): Flow<List<FavsIDE>> {
        return getAllIDE()
            .map { ides ->
                ides.filter { favIDE ->
                    favIDE.isFav
                }
            }
    }

    companion object {
        @Volatile
        private var instance: IDERepository? = null

        fun getInstance(): IDERepository =
            instance ?: synchronized(this) {
                IDERepository().apply {
                    instance = this
                }
            }
    }
}