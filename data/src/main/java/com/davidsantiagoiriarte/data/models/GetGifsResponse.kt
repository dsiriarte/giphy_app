package com.davidsantiagoiriarte.data.models

data class GetGifsResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)