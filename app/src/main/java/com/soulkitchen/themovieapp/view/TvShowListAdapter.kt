package org.koin.sampleapp.view.result

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.net.Uri
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.soulkitchen.themovieapp.R
import com.soulkitchen.themovieapp.repository.data.Results
import org.koin.sampleapp.view.result.Constants.IMAGE_URL

class TvShowListAdapter(var list: List<Results>, private val onClick: (Results) -> Unit, var context: Context) :
    PagedListAdapter<Results, ResultHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        getItem(position)?.let { (holder as ResultHolder).bindTo(it, onClick) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_movie, parent, false)
        return ResultHolder.create(item)
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Results>() {
            override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
                return oldItem == newItem
            }
        }
    }
}

object Constants {
    const val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"
    const val IMAGE_SIZE = "w500"
    const val IMAGE_URL = IMAGE_BASE_URL + IMAGE_SIZE

}

class ResultHolder(item: View) : RecyclerView.ViewHolder(item) {

    val title = item.findViewById<TextView>(R.id.tvTitleMovie)
    val titleeAvarageRate = item.findViewById<TextView>(R.id.tvRateMovie)
    val imageMovie = item.findViewById<ImageView>(R.id.immage_movie_poster)
    val context = item.context;
    val cardView = item.findViewById<CardView>(R.id.card_view)

    fun bindTo(model: Results, onClick: (Results) -> Unit) {
        cardView.setOnClickListener { onClick(model) }

        title.text = model.name
        titleeAvarageRate.text = model.vote_average.toString()
        Glide.with(context)
            .load(Uri.parse(IMAGE_URL + model.poster_path))
            .into(imageMovie)
    }

    companion object {

        fun create(parent: View): ResultHolder {


            return ResultHolder(parent)
        }
    }
}