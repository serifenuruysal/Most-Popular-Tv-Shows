package com.soulkitchen.themovieapp

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.sampleapp.di.localAndroidDatasourceModule
import org.koin.sampleapp.di.movieDbApp

class MainApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, movieDbApp + localAndroidDatasourceModule);
    }
}