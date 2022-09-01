package com.galexdev.finalproject.ui.detail

import androidx.databinding.BindingAdapter
import com.galexdev.finalproject.domain.Movie

@BindingAdapter("movie")
fun MovieDetailInfoView.updateMovieDetails(movie: Movie?) {
    if (movie != null) {
        setMovie(movie)
    }
}