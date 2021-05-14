package com.davidsantiagoiriarte.data.util

import com.davidsantiagoiriarte.data.db.models.FavoriteGif
import com.davidsantiagoiriarte.data.network.models.Data
import com.davidsantiagoiriarte.domain.models.Gif

fun Data.map(isFavorite: Boolean, localGifsUrl: String?): Gif {
    return Gif(
        id,
        "https://media.giphy.com/media/$id/giphy.gif",
        localGifsUrl,
        title,
        isFavorite
    )
}

fun FavoriteGif.map(): Gif {
    return Gif(
        id,
        gifUrl,
        localGifUrl,
        title,
        true
    )
}

fun Gif.map(): FavoriteGif {
    return FavoriteGif(
        id,
        gifUrl,
        localGifUrl,
        title
    )
}
