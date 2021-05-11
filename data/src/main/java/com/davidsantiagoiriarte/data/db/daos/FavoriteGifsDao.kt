package com.davidsantiagoiriarte.data.db.daos

import androidx.room.*
import com.davidsantiagoiriarte.data.db.models.FavoriteGif
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGifsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteGif: FavoriteGif)

    @Query("SELECT * FROM FavoriteGif WHERE title LIKE :search")
    fun getFavoriteGifs(search: String): Flow<List<FavoriteGif>>

    @Query("SELECT * FROM FavoriteGif")
    suspend fun getAllFavoriteGifs(): List<FavoriteGif>

    @Delete
    suspend fun deleteFavoriteGif(favoriteGif: FavoriteGif)
}
