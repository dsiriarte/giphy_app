package com.davidsantiagoiriarte.data.repositories

import com.davidsantiagoiriarte.data.db.daos.RecentSearchDao
import com.davidsantiagoiriarte.data.db.models.RecentSearch
import com.davidsantiagoiriarte.domain.repositories.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class RecentSearchRepositoryImpl(private val recentSearchDao: RecentSearchDao) :
    RecentSearchRepository {

    override fun getRecentSearch(): Flow<List<String>> {
        return recentSearchDao.getRecentSearch().transform { list ->
            emit(list.map { it.searchQuery })
        }
    }

    override suspend fun insert(search: String) {
        recentSearchDao.insert(RecentSearch(search))
    }

}
