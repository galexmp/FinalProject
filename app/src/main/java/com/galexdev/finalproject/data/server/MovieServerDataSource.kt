package com.galexdev.finalproject.data.server

import arrow.core.Either
import com.galexdev.finalproject.data.datasource.MovieRemoteDataSource
import com.galexdev.finalproject.data.tryCall
import com.galexdev.finalproject.di.ApiKey
import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie
import javax.inject.Inject
import com.galexdev.finalproject.data.server.RemoteMovie as RemoteMovie

class MovieServerDataSource @Inject constructor(@ApiKey private val apiKey: String, private val remoteService: RemoteService) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        remoteService
            .listPopularMovies(apiKey, region)
            .results
            .toDomainModel()
    }
}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )