package com.davidsantiagoiriarte.data.repositories

import com.davidsantiagoiriarte.data.db.daos.FavoriteGifsDao
import com.davidsantiagoiriarte.data.network.GiphyService
import com.davidsantiagoiriarte.data.util.API_KEY
import com.davidsantiagoiriarte.data.util.INITIAL_OFFSET
import com.davidsantiagoiriarte.data.util.RESULTS_LIMIT
import com.davidsantiagoiriarte.data.util.map
import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.domain.models.GifsResult
import com.davidsantiagoiriarte.domain.repositories.PagedGifsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

class ApiGifsRepository(
    private val service: GiphyService,
    private val favoriteGifsDao: FavoriteGifsDao
) : PagedGifsRepository {

    private val inMemoryGifs = mutableListOf<Gif>()
    private val gifsResults = MutableSharedFlow<GifsResult>(replay = 1)
    private var lastOffset = INITIAL_OFFSET
    private var totalSearchItems = 0
    private var isRequestInProgress = false
    private var currentQuery: String? = null

    override suspend fun getGifs(searchQuery: String?): Flow<GifsResult> {
        lastOffset = INITIAL_OFFSET
        inMemoryGifs.clear()
        currentQuery = searchQuery
        requestData()
        return gifsResults
    }

    override suspend fun getMoreResults(searchQuery: String?) {
        if (isRequestInProgress) return
        currentQuery = searchQuery
        val successful = requestData()
        if (successful && lastOffset < (totalSearchItems - RESULTS_LIMIT)) {
            lastOffset += RESULTS_LIMIT
        }
    }

    override suspend fun addGifToFavorite(gif: Gif) {
        favoriteGifsDao.insert(gif.map())
    }

    override suspend fun removeFavoriteGif(gif: Gif) {
        favoriteGifsDao.deleteFavoriteGif(gif.map())
    }

    private suspend fun requestData(): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val searchResponse = if (currentQuery.isNullOrEmpty()) service.getTrendingGifs(
                API_KEY,
                RESULTS_LIMIT,
                lastOffset
            ) else service.searchGifs(
                API_KEY,
                currentQuery!!,
                RESULTS_LIMIT,
                lastOffset
            )
            totalSearchItems = searchResponse.pagination.total_count
            val favoriteGifs = favoriteGifsDao.getAllFavoriteGifs()
            val items = searchResponse.data.map { data ->
                val isFavorite = favoriteGifs.firstOrNull { it.id == data.id } != null
                data.map(isFavorite)
            }
            inMemoryGifs.addAll(items)
            gifsResults.emit(GifsResult.Success(inMemoryGifs))
            successful = true
        } catch (exception: IOException) {
            gifsResults.emit(GifsResult.Error(exception))
        } catch (exception: HttpException) {
            gifsResults.emit(GifsResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

}
