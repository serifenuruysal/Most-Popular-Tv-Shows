package com.soulkitchen.themovieapp.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.soulkitchen.themovieapp.repository.MovieDataSource
import com.soulkitchen.themovieapp.repository.MovieDataSourceFactory
import com.soulkitchen.themovieapp.repository.MovieService
import com.soulkitchen.themovieapp.repository.State
import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import com.soulkitchen.themovieapp.repository.data.Results
import com.soulkitchen.themovieapp.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MovieMainViewModel(
    private val movieService: MovieService,
    private val schedulerProvider: SchedulerProvider
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

    fun getState(): LiveData<State> = Transformations.switchMap<MovieDataSource,
            State>(movieDataSourceFactory.movieDataSourceLiveData, MovieDataSource::state)

    fun retry() {
        movieDataSourceFactory.movieDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return movieList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

//    val getPopularTvShowsEvent = SingleLiveEvent<GetPopularTvShowsEvent>()
//
//    fun getPopulerTvShows(page: Int) {
//        launch {
//            getPopularTvShowsEvent.value = GetPopularTvShowsEvent()
//            movieRepository.getPopularTvShows(page).with(schedulerProvider)
//                .subscribe(
//                    { response -> getPopularTvShowsEvent.postValue(GetPopularTvShowsEvent(response = response)) },
//                    { error -> GetPopularTvShowsEvent(error = error) })
//        }
//    }
}

data class GetPopularTvShowsEvent(
    val isLoading: Boolean = false,
    val response: MovieDbResponse? = null,
    val error: Throwable? = null
)
