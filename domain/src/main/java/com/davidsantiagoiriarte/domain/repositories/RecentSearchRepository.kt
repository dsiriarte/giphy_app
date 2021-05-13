package com.davidsantiagoiriarte.domain.repositories

import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    fun getRecentSearch(): Flow<List<String>>
    suspend fun insert(search: String)
}
