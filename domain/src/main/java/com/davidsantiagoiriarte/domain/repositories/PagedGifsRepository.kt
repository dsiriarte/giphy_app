package com.davidsantiagoiriarte.domain.repositories

import com.davidsantiagoiriarte.domain.models.Gif

interface PagedGifsRepository : GifsRepository {
    suspend fun getMoreResults(searchQuery: String?)
    suspend fun addGifToFavorite(gif: Gif)
}
