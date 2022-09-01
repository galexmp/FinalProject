package com.galexdev.finalproject.ui.detail

import app.cash.turbine.test
import com.galexdev.finalproject.data.server.RemoteMovie
import com.galexdev.finalproject.data.database.Movie as DatabaseMovie
import com.galexdev.finalproject.testrules.CoroutineTestRule
import com.galexdev.finalproject.ui.buildDatabaseMovies
import com.galexdev.finalproject.ui.buildRepositoryWith
import com.galexdev.finalproject.ui.detail.DetailViewModel.UiState
import com.galexdev.finalproject.usecases.FindMovieUseCase
import com.galexdev.finalproject.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(2, awaitItem().movie!!.id)
            cancel()
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseMovies(1, 2, 3)
        )

        vm.onFavoriteClicked()

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(false, awaitItem().movie!!.favorite)
            Assert.assertEquals(true, awaitItem().movie!!.favorite)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<DatabaseMovie> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val findMovieUseCase = FindMovieUseCase(moviesRepository)
        val switchMovieFavoriteUseCase = SwitchMovieFavoriteUseCase(moviesRepository)
        return DetailViewModel(id, findMovieUseCase, switchMovieFavoriteUseCase)
    }
}