package com.davidsantiagoiriarte.presentation.gifslist

import com.davidsantiagoiriarte.domain.models.Gif

interface FavoriteItemClickListener {
    fun onFavoriteClicked(gif: Gif)
}
