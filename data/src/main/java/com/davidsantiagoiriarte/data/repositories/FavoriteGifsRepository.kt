package com.davidsantiagoiriarte.data.repositories

import com.davidsantiagoiriarte.data.db.daos.FavoriteGifsDao
import com.davidsantiagoiriarte.data.util.map
import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.domain.models.GifsResult
import com.davidsantiagoiriarte.domain.repositories.GifsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class FavoriteGifsRepository(private val favoriteGifsDao: FavoriteGifsDao) :
    GifsRepository {

    override suspend fun removeFavoriteGif(gif: Gif) {
        favoriteGifsDao.deleteFavoriteGif(gif.map())
    }

    override suspend fun getGifs(searchQuery: String?): Flow<GifsResult> {
        val query = searchQuery ?: ""
        return favoriteGifsDao.getFavoriteGifs("%$query%").transform { gifs ->
            emit(GifsResult.Success(gifs.map { it.map() }, true))
        }
    }

}
