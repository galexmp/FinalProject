package com.galexdev.finalproject.usecases

import com.galexdev.finalproject.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import com.galexdev.finalproject.domain.Movie
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository){

    operator fun invoke(): Flow<List<Movie>> = repository.popularMovies
}