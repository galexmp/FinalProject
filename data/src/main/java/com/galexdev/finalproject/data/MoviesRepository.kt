package com.galexdev.finalproject.data

import com.galexdev.finalproject.data.datasource.MovieLocalDataSource
import com.galexdev.finalproject.data.datasource.MovieRemoteDataSource
import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by GalexMP on 30/06/2022
 */
class MoviesRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {
    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            movies.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(movie: Movie): Error? {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        return localDataSource.save(listOf(updatedMovie))
    }
}


