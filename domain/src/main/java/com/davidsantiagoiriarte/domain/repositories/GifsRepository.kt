package com.davidsantiagoiriarte.domain.repositories

import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.domain.models.GifsResult
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    suspend fun getGifs(searchQuery: String?): Flow<GifsResult>
    suspend fun removeFavoriteGif(gif: Gif)
}
