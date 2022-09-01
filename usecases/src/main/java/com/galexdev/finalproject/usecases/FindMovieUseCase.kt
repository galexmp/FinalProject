package com.galexdev.finalproject.usecases

import com.galexdev.finalproject.data.MoviesRepository
import javax.inject.Inject

class FindMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    operator fun invoke(id: Int) = repository.findById(id)
}