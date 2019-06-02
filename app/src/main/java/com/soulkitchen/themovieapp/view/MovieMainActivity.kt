package com.soulkitchen.themovieapp.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.soulkitchen.themovieapp.R
import com.soulkitchen.themovieapp.repository.data.MovieDbResponse
import com.soulkitchen.themovieapp.repository.data.Results
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel
import org.koin.sampleapp.view.result.TvShowListAdapter

class MovieMainActivity : AppCompatActivity() {

    val myModel: MovieMainViewModel by viewModel()
    private lateinit var tvShowListAdapter: TvShowListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("onCreate ...")

        tvShowListAdapter = TvShowListAdapter(emptyList(), onItemClick(), this)
        rvTvShows.adapter = tvShowListAdapter
        rvTvShows.layoutManager = LinearLayoutManager(this)
        myModel.movieList.observe(this, Observer {
            tvShowListAdapter.submitList(it)
        })
    }

    private fun onItemClick(): (Results) -> Unit {
        return {

        }
    }


    fun displayNormal() {
        progress.visibility = View.GONE
    }

    fun displayProgress() {
        progress.visibility = View.VISIBLE
    }

    fun showTvShowSuccess(response: MovieDbResponse) {
        tvShowListAdapter.list = response.results
        tvShowListAdapter.notifyDataSetChanged()
        Snackbar.make(layout, "Got Success :", Snackbar.LENGTH_LONG).show()
    }

    fun showTvShowFailed(error: Throwable?) {
        Snackbar.make(layout, "Got error : $error", Snackbar.LENGTH_LONG).show()
    }

}
