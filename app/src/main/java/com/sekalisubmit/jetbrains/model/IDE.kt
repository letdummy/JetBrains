package com.sekalisubmit.jetbrains.model

data class IDE(
    val id: Long,
    val image: Int,
    val title: String,
    val subtitle: String,
    val category: String,
    val description: String,
    val ticker: String
)