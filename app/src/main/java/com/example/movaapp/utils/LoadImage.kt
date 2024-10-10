package com.example.movaapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movaapp.R

fun ImageView.loadImageUrl(url: String) {
    val options = RequestOptions().centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()
    Glide.with(this).load(imageBase_url + url).apply(options)
        .placeholder(R.drawable.movalogo).into(this)
}


fun ImageView.loadProfileImageUrl(url: String) {
    val options = RequestOptions().centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()
    Glide.with(this).load(url).apply(options)
        .placeholder(R.drawable.movalogo).into(this)
}

