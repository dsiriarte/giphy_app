package com.davidsantiagoiriarte.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteGif(
    @PrimaryKey val id: String,
    val gifUrl: String,
    val localGifUrl: String?,
    val title: String
)
