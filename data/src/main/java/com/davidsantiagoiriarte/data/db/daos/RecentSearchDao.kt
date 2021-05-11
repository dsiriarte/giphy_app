package com.davidsantiagoiriarte.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davidsantiagoiriarte.data.db.models.RecentSearch

@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(search: RecentSearch)

    @Query("SELECT * FROM RecentSearch LIMIT 10")
    fun getRecentSearch(): LiveData<List<RecentSearch>>
}
