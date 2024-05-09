package com.example.favorite.di

import com.example.favorite.FavoriteUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteUserViewModel(get()) }
}