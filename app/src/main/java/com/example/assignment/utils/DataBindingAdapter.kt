package com.example.assignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import assignment.R
import com.squareup.picasso.Picasso

class DataBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImage(imageView: ImageView, url: String) {
            val modifiedUrl = url.replace("https","http")
            Picasso.get().load(modifiedUrl).placeholder(R.drawable.ic_launcher_background)
                .into(imageView)

        }
    }
}