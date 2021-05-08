package com.davidsantiagoiriarte.domain.repositories

import com.davidsantiagoiriarte.domain.models.Gif
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    suspend fun getTrendingGifs(): Flow<Gif>
    suspend fun searchGifs(searchQuery: String): Flow<Gif>
    fun getMore()
}
