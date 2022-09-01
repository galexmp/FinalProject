package com.galexdev.finalproject.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.galexdev.finalproject.di.MovieId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {
    @Provides
    @ViewModelScoped
    @MovieId
    fun provideMovieId(savedStateHandle: SavedStateHandle) =
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).movieId
}