package com.davidsantiagoiriarte.domain.repositories

import androidx.lifecycle.LiveData

interface RecentSearchRepository {
    fun getRecentSearch(): LiveData<List<String>>
    suspend fun insert(search: String)
}
