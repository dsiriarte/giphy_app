package com.davidsantiagoiriarte.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.davidsantiagoiriarte.data.db.models.RecentSearch
import com.davidsantiagoiriarte.data.db.daos.RecentSearchDao
import com.davidsantiagoiriarte.domain.repositories.RecentSearchRepository

class RecentSearchRepositoryImpl(private val recentSearchDao: RecentSearchDao) :
    RecentSearchRepository {

    override fun getRecentSearch(): LiveData<List<String>> {
        return map(recentSearchDao.getRecentSearch()) { list ->
            list.map { it.searchQuery }
        }
    }

    override suspend fun insert(search: String) {
        recentSearchDao.insert(RecentSearch(search))
    }

}
