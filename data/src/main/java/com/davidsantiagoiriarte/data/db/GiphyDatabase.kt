package com.davidsantiagoiriarte.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidsantiagoiriarte.data.db.daos.FavoriteGifsDao
import com.davidsantiagoiriarte.data.db.daos.RecentSearchDao
import com.davidsantiagoiriarte.data.db.models.FavoriteGif
import com.davidsantiagoiriarte.data.db.models.RecentSearch

@Database(entities = [RecentSearch::class, FavoriteGif::class], version = 1)
abstract class GiphyDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
    abstract fun favoriteGifsDao(): FavoriteGifsDao
}
