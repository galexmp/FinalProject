package com.galexdev.finalproject.data.server

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by GalexMP on 29/06/2022
 */
interface RemoteService {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): RemoteResult

}