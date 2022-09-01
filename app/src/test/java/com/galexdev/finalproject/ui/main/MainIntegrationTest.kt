package com.galexdev.finalproject.ui.main

import app.cash.turbine.test
import com.galexdev.finalproject.data.server.RemoteMovie
import com.galexdev.finalproject.data.database.Movie as DatabaseMovie
import com.galexdev.finalproject.testrules.CoroutineTestRule
import com.galexdev.finalproject.ui.buildDatabaseMovies
import com.galexdev.finalproject.ui.buildRemoteMovies
import com.galexdev.finalproject.ui.buildRepositoryWith
import com.galexdev.finalproject.ui.main.MainViewModel.UiState
import com.galexdev.finalproject.usecases.GetPopularMoviesUseCase
import com.galexdev.finalproject.usecases.RequestPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = emptyList()), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = true), awaitItem())
            assertEquals(UiState(movies = emptyList(), loading = false), awaitItem())

            val movies = awaitItem().movies!!
            assertEquals("Title 4", movies[0].title)
            assertEquals("Title 5", movies[1].title)
            assertEquals("Title 6", movies[2].title)

            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseMovies(1, 2, 3)
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())

            val movies = awaitItem().movies!!
            assertEquals("Title 1", movies[0].title)
            assertEquals("Title 2", movies[1].title)
            assertEquals("Title 3", movies[2].title)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): MainViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val getPopularMoviesUseCase = GetPopularMoviesUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase)
    }
}