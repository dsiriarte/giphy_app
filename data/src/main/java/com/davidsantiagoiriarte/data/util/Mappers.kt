package com.davidsantiagoiriarte.data.util

import com.davidsantiagoiriarte.data.db.models.FavoriteGif
import com.davidsantiagoiriarte.data.network.models.Data
import com.davidsantiagoiriarte.domain.models.Gif

fun Data.map(isFavorite: Boolean): Gif {
    return Gif(
        id,
        "https://media.giphy.com/media/$id/giphy.gif",
        title,
        isFavorite
    )
}

fun FavoriteGif.map(): Gif {
    return Gif(
        id,
        gifUrl,
        title,
        true
    )
}

fun Gif.map(): FavoriteGif {
    return FavoriteGif(
        id,
        gifUrl,
        title
    )
}
