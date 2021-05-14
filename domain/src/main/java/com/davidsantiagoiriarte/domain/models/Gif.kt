package com.davidsantiagoiriarte.domain.models

data class Gif(
    val id: String,
    val gifUrl: String,
    var localGifUrl: String?,
    val title: String,
    var isFavorite: Boolean = false
) {
    fun getRealUrl(): String = localGifUrl ?: gifUrl
}
