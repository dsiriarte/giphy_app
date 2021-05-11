package com.davidsantiagoiriarte.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davidsantiagoiriarte.domain.logger.AppLogger
import com.davidsantiagoiriarte.domain.repositories.GifsRepository
import com.davidsantiagoiriarte.domain.repositories.RecentSearchRepository
import com.davidsantiagoiriarte.presentation.gifslist.GifsViewModel

/**
 * Factory for ViewModels
 */
class ViewModelFactory(
    private val gifsRepository: GifsRepository,
    private val recentSearchRepository: RecentSearchRepository,
    private val appLogger: AppLogger
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GifsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GifsViewModel(gifsRepository, recentSearchRepository, appLogger) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
