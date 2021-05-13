package com.davidsantiagoiriarte.domain.models

data class Gif(
    val id: String,
    val gifUrl: String,
    val title: String,
    var isFavorite: Boolean = false
)
