package com.example.movaapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movaapp.R

fun ImageView.loadImageUrl(url:String){
    Glide.with(this).load(imageBase_url+url).placeholder(R.drawable.movalogo).error(R.drawable.apple).into(this)
}