package com.galexdev.finalproject.ui.main

import app.cash.turbine.test
import com.galexdev.finalproject.testrules.CoroutineTestRule
import com.galexdev.finalproject.ui.main.MainViewModel.UiState
import com.galexdev.finalproject.usecases.GetPopularMoviesUseCase
import com.galexdev.finalproject.usecases.RequestPopularMoviesUseCase
import com.galexdev.finalproject.usecases.sampleMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    private lateinit var vm: MainViewModel

    private var movies = listOf(sampleMovie.copy(id=1))

    @Before
    fun setUp(){

        whenever(getPopularMoviesUseCase()).thenReturn(flowOf(movies))
        vm = MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }


    @Test
    fun `State is updated with current cached content immediately`() = runTest{

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = movies), awaitItem())
            cancel()
        }

    }

    @Test
    fun `Progress is shown when screen start and hidden when it finishes requesting movies`() = runTest{

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = movies), awaitItem())
            assertEquals(UiState(movies = movies, loading = true), awaitItem())
            assertEquals(UiState(movies = movies, loading = false), awaitItem())
            cancel()
        }

    }
}