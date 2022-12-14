package com.galexdev.finalproject.ui.main

import androidx.lifecycle.*
import com.galexdev.finalproject.data.toError
import com.galexdev.finalproject.domain.Error
import com.galexdev.finalproject.domain.Movie
import com.galexdev.finalproject.usecases.GetPopularMoviesUseCase
import com.galexdev.finalproject.usecases.RequestPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
): ViewModel() {

    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) }}
                .collect{movies -> _state.update { UiState(movies = movies)}
            }
        }
    }

    fun onUiReady(){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestPopularMoviesUseCase()
            _state.update { _state.value.copy(loading = false, error = error)}
        }
    }


    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )
}