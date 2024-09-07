package com.example.movaapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.movaapp.R

@BindingAdapter("load_image_url")
fun loadImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.loadImageUrl(url)
    }?: run {
        imageView.setImageResource(R.drawable.movologo)
    }

}