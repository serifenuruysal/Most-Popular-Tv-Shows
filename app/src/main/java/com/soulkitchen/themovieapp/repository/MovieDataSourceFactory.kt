package com.soulkitchen.themovieapp.repository


import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.soulkitchen.themovieapp.repository.data.Results
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val service: MovieService
) : DataSource.Factory<Int, Results>() {

    val movieDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Results> {
        val movieDataSource = MovieDataSource(service, compositeDisposable)
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}