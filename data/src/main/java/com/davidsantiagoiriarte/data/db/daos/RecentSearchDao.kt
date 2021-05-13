package com.davidsantiagoiriarte.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davidsantiagoiriarte.data.db.models.RecentSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(search: RecentSearch)

    @Query("SELECT * FROM RecentSearch LIMIT 10")
    fun getRecentSearch(): Flow<List<RecentSearch>>
}
