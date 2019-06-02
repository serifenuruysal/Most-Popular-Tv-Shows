package com.soulkitchen.themovieapp.view

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

abstract class BaseViewModel : ViewModel() {
    companion object {
        /**
         * download image from url and set imageView. This method is in use by xml for databinding
         *
         * @param view imageView
         * @param url  image url
         */
        @BindingAdapter("imageUrl")
        fun setImage(view: ImageView, url: String) {
            val requestManager = Glide.with(view.context)
            val requestBuilder = requestManager.load(url)
            requestBuilder.into(view)
        }

        @BindingAdapter("backgroundResource")
        fun setBackgroundResource(view: View, resId: Int) {
            view.setBackgroundResource(resId)
        }
    }


}