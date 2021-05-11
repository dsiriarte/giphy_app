package com.davidsantiagoiriarte.data.network.models

data class GifsResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)