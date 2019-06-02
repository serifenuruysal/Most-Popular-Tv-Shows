package org.koin.sampleapp.di

import com.soulkitchen.themovieapp.repository.MovieDataSource
import com.soulkitchen.themovieapp.repository.MovieService
import com.soulkitchen.themovieapp.repository.local.LocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.repository.local.AndroidJsonReader
import org.koin.sampleapp.repository.local.JsonReader


val localAndroidDatasourceModule = applicationContext {
    bean { AndroidJsonReader(androidApplication()) as JsonReader }
    bean { LocalDataSource(get()) as MovieService }

}