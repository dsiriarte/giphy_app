package com.davidsantiagoiriarte.domain.models

sealed class GifsResult {
    data class Success(val gifs: List<Gif>, val isFirstTimeCall : Boolean) : GifsResult()
    data class Error(val error: Exception) : GifsResult()
}
