package com.davidsantiagoiriarte.data.network.models

data class Pagination(
    val count: Int,
    val offset: Int,
    val total_count: Int
)