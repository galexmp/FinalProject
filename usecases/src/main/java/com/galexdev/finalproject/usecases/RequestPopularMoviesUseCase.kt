package com.galexdev.finalproject.usecases

import com.galexdev.finalproject.data.MoviesRepository
import com.galexdev.finalproject.domain.Error
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? = moviesRepository.requestPopularMovies()
}