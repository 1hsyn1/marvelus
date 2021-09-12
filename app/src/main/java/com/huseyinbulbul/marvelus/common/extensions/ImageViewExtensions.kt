package com.huseyinbulbul.marvelus.common.extensions

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:ImageUrl")
fun loadImageUrl(imageView: ImageView, url: String?){
    url?.let {
        Picasso.with(imageView.context)
            .load(it)
            .into(imageView)
    }
}