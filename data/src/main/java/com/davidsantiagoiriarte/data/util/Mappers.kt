package com.davidsantiagoiriarte.data.util

import com.davidsantiagoiriarte.data.models.Data
import com.davidsantiagoiriarte.domain.models.Gif

fun Data.map(): Gif {
    return Gif(
        id,
        embed_url,
        title
    )
}
