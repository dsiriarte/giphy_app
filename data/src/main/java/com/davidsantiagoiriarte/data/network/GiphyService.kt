package com.davidsantiagoiriarte.data.network

import com.davidsantiagoiriarte.data.network.models.GifsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("trending")
    suspend fun getTrendingGifs(
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
    ): GifsResponse

    @GET("search")
    suspend fun searchGifs(
        @Query("api_key")
        apiKey: String,
        @Query("q")
        query: String,
        @Query("limit")
        limit: Int?,
        @Query("offSet")
        offSet: Int?,
        @Query("rating")
        rating: String? = null,
        @Query("lang")
        language: String? = null,
        @Query("random_id")
        randomId: String? = null
    ): GifsResponse
}
