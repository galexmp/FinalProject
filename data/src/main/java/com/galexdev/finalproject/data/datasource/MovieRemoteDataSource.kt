package com.galexdev.finalproject.data.datasource

import arrow.core.Either
import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}