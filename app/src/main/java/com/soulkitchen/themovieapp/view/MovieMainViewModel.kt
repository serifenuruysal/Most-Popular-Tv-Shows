package com.soulkitchen.themovieapp.view

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.soulkitchen.themovieapp.repository.MovieDataSourceFactory
import com.soulkitchen.themovieapp.repository.MovieService
import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import com.soulkitchen.themovieapp.repository.data.Results
import io.reactivex.disposables.CompositeDisposable

class MovieMainViewModel(
    private val movieService: MovieService
) : AbstractViewModel() {
    var movieList: LiveData<PagedList<Results>>
    private val compositeDisposable = CompositeDisposable()
    private val movieDataSourceFactory: MovieDataSourceFactory
    init {
        movieDataSourceFactory = MovieDataSourceFactory(compositeDisposable, movieService)
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10 * 2)
            .setEnablePlaceholders(false)
            .build()
        movieList = LivePagedListBuilder<Int, Results>(movieDataSourceFactory, config).build()
    }


    fun retry() {
        movieDataSourceFactory.movieDataSourceLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}

data class GetPopularTvShowsEvent(
    val isLoading: Boolean = false,
    val response: MovieDbResponse? = null,
    val error: Throwable? = null
)
