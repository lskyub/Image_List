package com.sklim.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sklim.R
import com.sklim.BR
import com.sklim.domain.model.Images

class ImageAdapter : PagingDataAdapter<Images.RS, ImageAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Images.RS>() {
        override fun areItemsTheSame(oldItem: Images.RS, newItem: Images.RS): Boolean {
            return oldItem.id == newItem.id && oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: Images.RS, newItem: Images.RS): Boolean {
            return oldItem.id == newItem.id && oldItem.toString() == newItem.toString()
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(R.layout.item_image, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.also {
            holder.binding.setVariable(BR.item, it)
        }
    }

    inner class ViewHolder(
        @LayoutRes layoutResId: Int,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    ) {
        val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!
    }
}