package com.galexdev.finalproject.usecases

import com.galexdev.finalproject.data.MoviesRepository
import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie
import javax.inject.Inject

class SwitchMovieFavoriteUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = repository.switchFavorite(movie)
}