package org.koin.sampleapp.di

import com.soulkitchen.themovieapp.repository.MovieRepository
import com.soulkitchen.themovieapp.repository.MovieRepositoryImpl
import com.soulkitchen.themovieapp.util.rx.ApplicationSchedulerProvider
import com.soulkitchen.themovieapp.util.rx.SchedulerProvider
import com.soulkitchen.themovieapp.view.MovieMainViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val tvMovieModule = applicationContext {

    // ViewModel for Main View
    viewModel { MovieMainViewModel(get()) }

    // MovieRepositoryRepository
    bean { MovieRepositoryImpl(get()) as MovieRepository }

//    bean { `MovieDataSourceFactory.kt`(get(), get()) }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

// Gather all app modules
val movieDbApp = listOf(tvMovieModule, rxModule)
