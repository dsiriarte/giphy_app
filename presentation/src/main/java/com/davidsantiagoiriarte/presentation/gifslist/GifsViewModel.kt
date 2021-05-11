package com.davidsantiagoiriarte.presentation.gifslist

import androidx.lifecycle.*
import com.davidsantiagoiriarte.domain.logger.AppLogger
import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.domain.models.GifsResult
import com.davidsantiagoiriarte.domain.repositories.GifsRepository
import com.davidsantiagoiriarte.domain.repositories.PagedGifsRepository
import com.davidsantiagoiriarte.domain.repositories.RecentSearchRepository
import com.davidsantiagoiriarte.presentation.errors.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class GifsViewModel(
    private val gifsRepository: GifsRepository,
    private val recentSearchRepository: RecentSearchRepository,
    private val appLogger: AppLogger
) : ViewModel() {

    val errorLiveData = MutableLiveData<Error>()
    val isLoading = MutableLiveData<Boolean>()

    private val queryLiveData = MutableLiveData<String?>()

    val itemsResult: LiveData<GifsResult> = queryLiveData.switchMap { queryString ->
        liveData {
            try {
                val result =
                    gifsRepository.getGifs(queryString).asLiveData(Dispatchers.Main)
                emitSource(result)
            } catch (ex: Exception) {
                handleException(ex)
            }
            isLoading.postValue(false)
        }
    }

    val recentSearchLiveData = recentSearchRepository.getRecentSearch()

    fun searchGifs(queryString: String) {
        addRecentSearch(queryString)
        isLoading.postValue(true)
        queryLiveData.postValue(queryString)
    }

    private fun addRecentSearch(queryString: String) {
        viewModelScope.launch {
            recentSearchRepository.insert(queryString)
        }
    }

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (visibleItemCount + lastVisibleItemPosition + 10 >= totalItemCount) {
            searchForMoreResults()
        }
    }

    private fun searchForMoreResults() {
        if (gifsRepository is PagedGifsRepository) {
            viewModelScope.launch {
                gifsRepository.getMoreResults(queryLiveData.value)
            }
        }
    }

    fun favoriteGifClicked(gif: Gif) {
        if (gif.isFavorite) {
            removeGifFromFavorite(gif)
        } else {
            addGifToFavorite(gif)
        }
    }

    private fun addGifToFavorite(gif: Gif) {
        if (gifsRepository is PagedGifsRepository) {
            viewModelScope.launch {
                gifsRepository.addGifToFavorite(gif)
            }
        }
    }

    private fun removeGifFromFavorite(gif: Gif) {
        viewModelScope.launch {
            gifsRepository.removeFavoriteGif(gif)
        }
    }

    fun handleException(exception: Exception) {
        appLogger.logError(exception)
        errorLiveData.postValue(
            when (exception) {
                is UnknownHostException -> Error.NoConnectionError
                else -> Error.DefaultError(exception.message)
            }
        )
    }
}
