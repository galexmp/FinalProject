package com.galexdev.finalproject.ui.detail

import app.cash.turbine.test
import com.galexdev.finalproject.testrules.CoroutineTestRule
import com.galexdev.finalproject.ui.detail.DetailViewModel.UiState
import com.galexdev.finalproject.usecases.FindMovieUseCase
import com.galexdev.finalproject.usecases.SwitchMovieFavoriteUseCase
import com.galexdev.finalproject.usecases.sampleMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest{

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var findMovieUseCase: FindMovieUseCase

    @Mock
    lateinit var switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val movie = sampleMovie.copy(id = 2)

    @Before
    fun setUp(){
        whenever(findMovieUseCase(id = 2)).thenReturn(flowOf(movie))
        vm = DetailViewModel(2, findMovieUseCase, switchMovieFavoriteUseCase)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = movie), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        vm.onFavoriteClicked()
        runCurrent()

        verify(switchMovieFavoriteUseCase).invoke(movie)
    }

}