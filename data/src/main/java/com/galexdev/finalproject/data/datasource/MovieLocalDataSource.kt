package com.galexdev.finalproject.data.datasource

import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<Movie>
    suspend fun save(movies: List<Movie>): Error?
}