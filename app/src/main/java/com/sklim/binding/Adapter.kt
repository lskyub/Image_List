package com.sklim.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sklim.adapter.ImageAdapter

object Adapter {

    @BindingAdapter("adapter")
    @JvmStatic
    fun RecyclerView.setAdapter(adapter: ImageAdapter?) {
        this.adapter = adapter
    }

    @BindingAdapter("url")
    @JvmStatic
    fun AppCompatImageView.setUrl(path: String?) {
        path?.also {
            Glide.with(context).load(path)
                .override(width, height)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
        }
    }
}