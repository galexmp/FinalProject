package com.galexdev.finalproject.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galexdev.finalproject.data.toError
import com.galexdev.finalproject.di.MovieId
import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie
import com.galexdev.finalproject.usecases.FindMovieUseCase
import com.galexdev.finalproject.usecases.SwitchMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @MovieId private val movieId : Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movie -> _state.update {  UiState(movie = movie) }}
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let {movie ->
            val error = switchMovieFavoriteUseCase(movie)
                _state.update { it.copy(error = error)}
            }
        }
    }

    data class UiState(val movie: Movie? = null, val error: Error? = null)
}

