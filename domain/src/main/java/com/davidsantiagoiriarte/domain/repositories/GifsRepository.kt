package com.davidsantiagoiriarte.domain.repositories

import com.davidsantiagoiriarte.domain.models.GifsResult
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    suspend fun getTrendingGifs(): Flow<GifsResult>
    suspend fun searchGifs(searchQuery: String)
    suspend fun getMoreResults()
}
