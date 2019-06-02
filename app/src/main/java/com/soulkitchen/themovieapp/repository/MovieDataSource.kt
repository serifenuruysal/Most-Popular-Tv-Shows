package com.soulkitchen.themovieapp.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import com.soulkitchen.themovieapp.repository.data.Results
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class MovieDataSource( val movieService: MovieService,  val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Int, Results>() {
    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Results>) {
        updateState(State.LOADING)
        compositeDisposable.add(movieService.getPopularMovies(1).subscribe(
            { response: MovieDbResponse? ->
                updateState(State.DONE)
                callback.onResult(response!!.results, null, 2)
            }, { error ->
                updateState(State.ERROR)
                setRetry(Action { loadInitial(params, callback) })
            }
        ))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {
        updateState(State.LOADING)
        compositeDisposable.add(movieService.getPopularMovies(params.key).subscribe(
            { response: MovieDbResponse? ->
                updateState(State.DONE)
                callback.onResult(response!!.results, params.key + 1)
            }, { error ->
                updateState(State.ERROR)
                setRetry(Action { loadAfter(params, callback) })
            }
        ))

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Results>) {

    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}

enum class State {
    DONE, LOADING, ERROR
}