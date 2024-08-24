package com.example.cookmate.ui.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageUrl(url: String) {
    Glide.with(this).load(url).into(this)
}
