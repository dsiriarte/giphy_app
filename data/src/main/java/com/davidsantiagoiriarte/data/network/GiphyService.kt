package com.davidsantiagoiriarte.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("trending")
    suspend fun getGifs(
        @Query("api_key")
        apiKey: String,
        @Query("limit")
        limit: Int?,
        @Query("offSet")
        offSet: Int?,
        @Query("rating")
        rating: String? = null,
        @Query("random_id")
        randomId: String? = null
    )
}
