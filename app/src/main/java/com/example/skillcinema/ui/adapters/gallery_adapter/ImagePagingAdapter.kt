package com.example.skillcinema.ui.adapters.gallery_adapter

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.marginBottom
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillcinema.R
import com.example.skillcinema.databinding.ItemGalleryBinding
import com.example.skillcinema.databinding.ItemMoviesBinding
import com.example.skillcinema.model.data.apiImages.Item
import com.example.skillcinema.model.data.apiTop.Film
import com.google.android.material.button.MaterialButton

class ImagePagingAdapter(private val clickListener: (pos: Int) -> Unit) :
    PagingDataAdapter<Item, ImagePagingAdapter.TasksVH>(DataDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksVH {
        val binding =
            ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksVH(binding)
    }

    override fun onBindViewHolder(holder: TasksVH, position: Int) {
        val task = getItem(position)
        holder.initView(task, position)
    }

    inner class TasksVH(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initView(movie: Item?, pos: Int) = with(binding) {

            container.setPadding(0, 0, 30, 30)

            Glide.with(itemView.context)
                .load(movie?.previewUrl)
                .into(imageView)

            itemView.setOnClickListener {
                clickListener(pos)
            }


        }

    }


    private object DataDiffUtilCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }


    }
}



