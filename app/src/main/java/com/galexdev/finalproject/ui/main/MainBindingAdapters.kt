package com.galexdev.finalproject.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.galexdev.finalproject.domain.Movie

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?){
    if (movies != null){
        (adapter as? MoviesAdapter)?.submitList(movies)
    }
}