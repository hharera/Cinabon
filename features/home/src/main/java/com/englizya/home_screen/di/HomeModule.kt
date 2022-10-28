package com.englizya.home_screen.di

import com.englizya.home_screen.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(get(), get(),get(),get())
    }
}